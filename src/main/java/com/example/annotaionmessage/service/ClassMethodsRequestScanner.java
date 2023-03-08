package com.example.annotaionmessage.service;

import com.example.annotaionmessage.annotation.MethodParam;
import com.example.annotaionmessage.annotation.PageNotes;
import com.example.annotaionmessage.annotation.RequestNotes;
import com.example.annotaionmessage.entity.Entry;
import com.example.annotaionmessage.entity.MessageObject;
import com.example.annotaionmessage.entity.MethodParams;
import com.example.annotaionmessage.utils.RequestAnalyse;
import com.example.annotaionmessage.utils.RequestItem;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ClassMethodsRequestScanner implements Scanner {

    private static Stack<Entry<MessageObject, String[]>> cache = new Stack<>();

    @Override
    public Entry<Entry<String[], String>, LinkedList<MessageObject>> scanRequestAnnotation(Class<?> clazz) {
        if(clazz.getAnnotation(RestController.class) == null) return null;

        Entry<String[], String> requestRootPath = getRequestRootPath(clazz);
        LinkedList<MessageObject> scannerResult = new LinkedList<>();
        if(!cache.isEmpty()){
            this.distributeMessageObject(requestRootPath, scannerResult);
        }
        for(Method method : clazz.getMethods()){
            MessageObject message = null;
            RequestNotes requestNotes = method.getAnnotation(RequestNotes.class);
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

            if(requestMapping == null) {
                // 得到当前方法的描述和请求地址
                Entry<String, String[]> classEntry = interfaceMethod(method);
                if(requestNotes != null && !classEntry.isEmpty()){
                    message = new MessageObject().setDescription(requestNotes.description())
                            .setReturnType(requestNotes.returnType().getName()).setParams(methodParams(requestNotes.requestParams()))
                            .setRequestMethod(classEntry.getKey()).setRequestPath(classEntry.getValue());

                }else if(requestNotes == null && !classEntry.isEmpty()){
                    message = new MessageObject().setDescription(classEntry.getKey()).setRequestPath(classEntry.getValue());
                }
            }else{
                message = methodsContainerRequestMapping(method).setReturnType(requestNotes.returnType().getName())
                        .setDescription(requestNotes.description()).setParams(methodParams(requestNotes.requestParams()));
            }
            addCache(requestNotes, message);
            if(message != null){
                if(requestRootPath.getKey() != null) message.setParentPath(requestRootPath.getKey());
                scannerResult.add(message);
            }
        }
        Entry<Entry<String[], String>, LinkedList<MessageObject>> result = new Entry<>(requestRootPath, scannerResult);
        return result;
    }


    private LinkedList<MethodParams> methodParams(MethodParam[] methodParams){
        LinkedList<MethodParams> list = new LinkedList<>();
        for (MethodParam methodParam : methodParams) {
            list.add(new MethodParams(methodParam.name(), methodParam.type(), methodParam.description()));
        }
        return list;
    }


    private void addCache(RequestNotes requestNotes, MessageObject messageObject){
        if(requestNotes != null && requestNotes.includePage().length != 0) {
            Entry<MessageObject, String[]> item = new Entry<>(messageObject, requestNotes.includePage());
            cache.push(item);
        }
    }


    private Entry<String, String[]> interfaceMethod(Method method){
        List<Entry<String , Class<? extends  Annotation>>> requestMethods = Arrays.asList(
                new Entry<>("Get", GetMapping.class), new Entry<>("Post", PostMapping.class),
                new Entry<>("Put", PutMapping.class), new Entry<>("Delete", DeleteMapping.class)
        );
        Entry<String, String[]> result = new Entry<>();
        for(int i = 0, length = requestMethods.size(); i < length; i++){
            Entry<String, Class<? extends Annotation>> classEntry = requestMethods.get(i);

            Annotation annotation = method.getAnnotation(classEntry.getValue());
            if(annotation != null)
                result = new Entry<>(classEntry.getKey(), RequestAnalyse.analyseRequestPath(classEntry.getKey(), annotation));
        }
        return result;
    }


    private Entry<String[], String> getRequestRootPath(Class<?> clazz){
        Entry<String[], String> result = new Entry<>();
        PageNotes pageNotes = clazz.getAnnotation(PageNotes.class);
        if(pageNotes != null) {
            result.setValue(pageNotes.description());
        }
        RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
        if(annotation != null){
            result.setKey(RequestAnalyse.analyseRequestPath(RequestItem.REQUESTMAPPING, annotation));
        }else
            result.setKey(new String[]{});
        return result;
    }


    private MessageObject methodsContainerRequestMapping(Method method){
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        MessageObject object = new MessageObject()
                .setRequestMethod(annotation.method()[0].name())
                .setRequestPath(RequestAnalyse.analyseRequestPath(RequestItem.REQUESTMAPPING, annotation));

        return object;
    }


    /**
     * @param classObject 当前类的地址信息
     * @param list 当前类所拥有的接口信息
     */
    private void distributeMessageObject(Entry<String[], String> classObject, LinkedList<MessageObject> list){
        Stack<Entry<MessageObject, String[]>> temp= new Stack<>();
        while (!cache.isEmpty()){
            Entry<MessageObject, String[]> pop = cache.pop(); // 得到栈中的元素
            //判断栈中的接口是否在当前类所在的页面使用到
            if(pathExits(classObject.getKey(), pop.getValue())){
                list.add(pop.getKey());
            }else
                temp.push(pop);
        }
        cache = temp;
    }


    /**
     * @param classPath 当前类所标注着的请求路径
     * @param methodPath 接口在在哪些页面中使用到，methodPath就是所使用处的地址
     * @return
     */
    private boolean pathExits(String[] classPath, String[] methodPath){
        List<String> list = Arrays.asList(methodPath);
        for(String item : classPath){
            if(list.contains(item)) return true;
        }
        return false;
    }



}
