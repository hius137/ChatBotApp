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
import android.widget.Toast;
import com.minhhieu.chatbotapp.R;
import com.minhhieu.chatbotapp.adapter.ResponsePicAdapter;
import com.minhhieu.chatbotapp.myclass.BuildConfig;
import com.minhhieu.chatbotapp.myclass.ResponsePic;

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


public class MyLibraryActivity extends AppCompatActivity {
    ImageView left_chat_response;
    EditText edt_input_pic;
    ImageView btn_send_mess_pic, btn_delete_pic, btn_back_pic;
    List<ResponsePic> responsePics = new ArrayList<>();
    ResponsePicAdapter responsePicAdapter;
    RecyclerView rcv_chat_pic;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_library);
        initUi();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rcv_chat_pic.setLayoutManager(llm);

        responsePicAdapter = new ResponsePicAdapter(responsePics);
        rcv_chat_pic.setAdapter(responsePicAdapter);
        initClick();
    }
        @SuppressLint("NotifyDataSetChanged")
        private void initClick() {
        btn_send_mess_pic.setOnClickListener((View v) -> {
            String text = edt_input_pic.getText().toString().trim();
            callApi(text);
            edt_input_pic.setText("");
            addToChat(ResponsePic.SENT_BY_ME, "", text);

        });
        btn_delete_pic.setOnClickListener((View v) -> {
            responsePics.clear();
            responsePicAdapter.notifyDataSetChanged();
        });
        btn_back_pic.setOnClickListener((View v) -> {
            Intent intent = new Intent(MyLibraryActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void callApi(String text) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("prompt", text);
            jsonBody.put("size", "256x256");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url(BuildConfig.API_URL_PIC)
                .header("Authorization", "Bearer "+BuildConfig.API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MyLibraryActivity.this, "Failed to load response", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    assert response.body() != null;
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String imgUrl = jsonObject.getJSONArray("data").getJSONObject(0).getString("url");
                    addToChat(ResponsePic.SENT_BY_BOT, imgUrl, null);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    void addToChat( String sentBy,String imageUrl, String message){
        runOnUiThread(() -> {
            responsePics.add(new ResponsePic(imageUrl,sentBy,message));
            responsePicAdapter.setData(responsePics);
            rcv_chat_pic.smoothScrollToPosition(responsePicAdapter.getItemCount());
        });
    }


    private void initUi() {

        btn_send_mess_pic = findViewById(R.id.btn_send_mess_pic);
        btn_delete_pic = findViewById(R.id.btn_delete_pic);
        btn_back_pic = findViewById(R.id.btn_back_pic);
        edt_input_pic = findViewById(R.id.edt_input_pic);
        left_chat_response = findViewById(R.id.left_chat_response);
        rcv_chat_pic = findViewById(R.id.rcv_chat_pic);
    }
}