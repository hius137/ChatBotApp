package com.minhhieu.chatbotapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.minhhieu.chatbotapp.R;
import com.minhhieu.chatbotapp.adapter.MessageAdapter;
import com.minhhieu.chatbotapp.myclass.BuildConfig;
import com.minhhieu.chatbotapp.myclass.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatBotActivity extends AppCompatActivity {

    RecyclerView rcv_chat;
    LinearLayout top_layout;
    LinearLayout welcome_layout;
    EditText edt_input;
    ImageView btn_send_mess, btn_back, btn_delete;
    List<Message> messageList = new ArrayList<>();
    MessageAdapter messageAdapter;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        initUi();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rcv_chat.setLayoutManager(llm);

        messageAdapter= new MessageAdapter(messageList);
        rcv_chat.setAdapter(messageAdapter);
        initClick();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void initClick() {
        btn_send_mess.setOnClickListener((View v) -> {
            String question = edt_input.getText().toString().trim();
                    if (question.isEmpty()){

                    }else {
                        addToChat(question,Message.SENT_BY_ME);
                        edt_input.setText("");
                        callApi(question);
                        welcome_layout.setVisibility(View.GONE);
                        messageAdapter.notifyDataSetChanged();
                    }
        });
        btn_back.setOnClickListener(v -> {
            Intent intent = new Intent(ChatBotActivity.this, MainActivity.class);
            startActivity(intent);
        });
        btn_delete.setOnClickListener(v -> {
            messageList.clear();
            messageAdapter.notifyDataSetChanged();
        });
    }

    private void initUi() {
        rcv_chat = findViewById(R.id.rcv_chat);
        welcome_layout = findViewById(R.id.welcome_layout);
        edt_input = findViewById(R.id.edt_input);
        btn_send_mess = findViewById(R.id.btn_send_mess);
        btn_back = findViewById(R.id.btn_back);
        btn_delete = findViewById(R.id.btn_delete);
        top_layout = findViewById(R.id.top_layout);
    }

    @SuppressLint("NotifyDataSetChanged")
    void addToChat(String message, String sentBy){
        runOnUiThread(() -> {
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                rcv_chat.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }
    void addToResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }
    void callApi(String question){
        //okhttp

        messageList.add(new Message("Typing...", Message.SENT_BY_BOT));
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "text-davinci-003");
            jsonBody.put("prompt", question);
            jsonBody.put("max_tokens", 4000);
            jsonBody.put("temperature", 0);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url(BuildConfig.API_URL)
                .header("Authorization", "Bearer "+BuildConfig.API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addToResponse("Failed to load response");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.   isSuccessful()){

                    JSONObject jsonObject;
                    try {
                        assert response.body() != null;
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addToResponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }else {
                    addToResponse("Failed to load response");
                }
            }
        });

    }
}