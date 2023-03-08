package com.example.annotaionmessage.entity;

import java.io.Serializable;

public class TransformBody implements Serializable {
    private static final long serialVersionUID = 1L;

    private String registerId;
    private MessageBody messageBody;

    public TransformBody(String registerId, MessageBody messageBody) {
        this.registerId = registerId;
        this.messageBody = messageBody;
    }

    public TransformBody() {}

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "TransformBody{" +
                "registerId='" + registerId + '\'' +
                ", messageBody=" + messageBody +
                '}';
    }
}

