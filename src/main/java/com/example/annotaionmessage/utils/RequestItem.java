package com.example.annotaionmessage.utils;

public enum RequestItem {

    GETMAPPING("GetMapping"),

    POSTMAPPING("PostMapping"),

    PUTMAPPING("PutMapping"),

    DELETEMAPPING("DeleteMapping"),

    REQUESTMAPPING("RequestMapping");



    public String description;

    RequestItem(String description){
        this.description = description;
    }


}
