package com.example.annotaionmessage.entity;

import java.io.Serializable;

public class MethodParams implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Class<?> type;
    private String description;

    public MethodParams(String name, Class<?> type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
