package com.example.annotaionmessage.entity;

import java.io.Serializable;

public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    private String contextPath;
    private int port;

    public Project(){}

    public Project(String contextPath, int port){
        this.contextPath = contextPath;
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Project{" +
                "contextPath='" + contextPath + '\'' +
                ", port=" + port +
                '}';
    }
}
