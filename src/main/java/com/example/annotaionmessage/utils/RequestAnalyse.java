package com.example.annotaionmessage.utils;

import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;


public class RequestAnalyse {

    public static String[] analyseRequestPath(RequestItem requestMethod, Annotation annotation){
        String[] result = null;
        switch (requestMethod){
            case GETMAPPING : result = isGetMapping(annotation); break;
            case POSTMAPPING : result = isPostMapping(annotation); break;
            case DELETEMAPPING : result = isDeleteMapping(annotation); break;
            case PUTMAPPING : result = isPutMapping(annotation); break;
            case REQUESTMAPPING : result = isRequestMapping(annotation); break;
        }
        return result;
    }

    public static String[] analyseRequestPath(String requestMethod, Annotation annotation){
        String[] result = null;
        if(requestMethod.equals("Get")){
            result = analyseRequestPath(RequestItem.GETMAPPING, annotation);
        }else if(requestMethod.equals("Post")){
            result = analyseRequestPath(RequestItem.POSTMAPPING, annotation);
        }else if(requestMethod.equals("Delete")){
            result = analyseRequestPath(RequestItem.DELETEMAPPING, annotation);
        }else
            result = analyseRequestPath(RequestItem.PUTMAPPING, annotation);
        return result;
    }


    private static String[] isGetMapping(Annotation annotation){
        GetMapping getMapping = (GetMapping) annotation;
        return list(getMapping.name(), getMapping.value(), getMapping.path());
    }

    private static String[] isPostMapping(Annotation annotation){
        PostMapping postMapping = (PostMapping) annotation;
        return list(postMapping.name(), postMapping.value(), postMapping.path());
    }

    private static String[] isDeleteMapping(Annotation annotation){
        DeleteMapping deleteMapping = (DeleteMapping) annotation;
        return list(deleteMapping.name(), deleteMapping.value(), deleteMapping.path());
    }

    private static String[] isRequestMapping(Annotation annotation){
        RequestMapping requestMapping = (RequestMapping) annotation;
        return list(requestMapping.name(), requestMapping.value(), requestMapping.path());
    }

    private static String[] isPutMapping(Annotation annotation){
        PutMapping putMapping = (PutMapping) annotation;
        return list(putMapping.name(), putMapping.value(), putMapping.path());
    }


    private static String[] list(String name, String[] value, String[] path){
        HashSet<String> list = new HashSet<>();
        if(name.length() != 0) list.add(name);
        if(value.length != 0) list.addAll(Arrays.asList(value));
        if(path.length != 0) list.addAll(Arrays.asList(path));
        String[] result = new String[list.size()];
        AtomicInteger index = new AtomicInteger();
        list.forEach(item -> {
            result[index.getAndIncrement()] = item;
        });
        return result;
    }
}
