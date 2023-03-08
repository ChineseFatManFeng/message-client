package com.example.annotaionmessage.service;


public class MessageServerConnection {

    private String address;
    private int port;

    public MessageServerConnection() {}

    public MessageServerConnection(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isEmpty(){
        return this.address == null && this.port == 0;
    }

    @Override
    public String toString() {
        return "MessageServerConnection{" +
                "address='" + address + '\'' +
                ", port=" + port +
                '}';
    }
}
