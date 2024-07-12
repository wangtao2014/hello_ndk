package com.example.hellondk;

public class Message {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;
    private final String content;
    private final int type;

    public Message(String content,int type){
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
