package com.example;

public class Message {

    String message;
    String email;
    String datetime;


    public Message() {
    }

    public Message(String email, String message, String datetime){
        this.email = email;
        this.message = message;
        this.datetime = datetime;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.email = name;
    }

    public String getdatetime() {
        return datetime;
    }

    public void setdatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", email='" + email + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';

    }
}
