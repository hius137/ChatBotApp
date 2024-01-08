package com.minhhieu.chatbotapp.myclass;

public class ResponsePic {

        public static String SENT_BY_ME = "me";
        public static String SENT_BY_BOT = "bot";

        private String sentBy;
        private String message;
        private String imageUrl;

        public String getSentBy() {
        return sentBy;
    }

        public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public ResponsePic( String imageUrl, String sentBy, String message) {
            this.message = message;
            this.imageUrl = imageUrl;
            this.sentBy = sentBy;
        }
}
