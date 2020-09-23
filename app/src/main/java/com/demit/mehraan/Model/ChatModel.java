package com.demit.mehraan.Model;

public class ChatModel {
        public String from;
        public String message;
        public String type;
        public String time,date,status;


    public ChatModel(String from, String message, String type, String time, String date, String status, String messageId) {
        this.from = from;
        this.message = message;
        this.type = type;
        this.time = time;
        this.date = date;
        this.status = status;
        this.messageId = messageId;
    }

    public ChatModel() {
    }

    public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String messageId;


        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


}
