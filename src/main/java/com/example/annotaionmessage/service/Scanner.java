package com.example.annotaionmessage.service;


import com.example.annotaionmessage.entity.Entry;
import com.example.annotaionmessage.entity.MessageObject;

import java.util.LinkedList;

public interface Scanner {

    Entry<Entry<String[], String>, LinkedList<MessageObject>> scanRequestAnnotation(Class<?> clazz);

}
