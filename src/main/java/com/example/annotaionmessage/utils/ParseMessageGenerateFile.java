package com.example.annotaionmessage.utils;

import com.example.annotaionmessage.annotation.MethodParam;
import com.example.annotaionmessage.entity.Entry;
import com.example.annotaionmessage.entity.MessageBody;
import com.example.annotaionmessage.entity.MessageObject;
import com.example.annotaionmessage.entity.MethodParams;

import java.io.*;
import java.util.LinkedList;

public class ParseMessageGenerateFile {

    private static final String ROOT_PATH = System.getProperty("user.dir");
    private static final String FILE_PATH = "\\src\\main\\resources\\annotation-message.md";
    private static final String NEXT_LINE = System.getProperty("line.separator");
    private static final String TABLE = "\t";
    private static final String DOUBLE_TABLE = "\t\t";


    public static void generatorFile(LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list){
        File file = new File(ROOT_PATH + FILE_PATH);
        if(file.exists()) file.delete();
        try {
            file.createNewFile();
            if(file.exists()) System.out.println("接口文件已生成: " + file.getAbsolutePath());
            FileWriter fileWriter = new FileWriter(file);
            for (Entry<Entry<String[], String>, LinkedList<MessageObject>> entryLinkedListEntry : list) {
                writerKey(entryLinkedListEntry.getKey(), fileWriter);
                writerValue(entryLinkedListEntry.getValue(), fileWriter);
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void writerKey(Entry<String[], String> key, FileWriter writer) throws IOException {
        StringBuilder builder = new StringBuilder();
        if(key.getValue() != null) builder.append(key.getValue()).append(":");
        for(String path : key.getKey()){
            builder.append(" ").append(path).append(",");
        }
        String content = builder.toString();
        if(content.length() != 0 && content.contains(",")){
            content = content.substring(0, builder.lastIndexOf(","));
        }else
            content = "此接口未作描述";
         writer.write(content + NEXT_LINE + NEXT_LINE);
    }


    private static void writerValue(LinkedList<MessageObject> value, FileWriter writer) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (MessageObject object : value) {
            builder.append(TABLE).append(object.getDescription()).append(NEXT_LINE);
            builder.append(DOUBLE_TABLE).append("请求方式: " + object.getRequestMethod()).append(NEXT_LINE);
            builder.append(DOUBLE_TABLE).append("请求地址: ").append(NEXT_LINE);
            for(String path : object.getRequestPath()){
                if(object.getParentPath() != null && object.getParentPath().length != 0) path = object.getParentPath()[0] + path;
                builder.append(DOUBLE_TABLE + TABLE).append(path).append(NEXT_LINE);
            }
            builder.append(DOUBLE_TABLE).append("请求参数: ").append(NEXT_LINE);
            if(object.getParams() != null){
                for (MethodParams param : object.getParams()) {
                    String content = (param.getName() + ": " + param.getDescription() + TABLE + "[" + param.getType().getName() + "]");
                    builder.append(DOUBLE_TABLE + TABLE).append(content).append(NEXT_LINE);
                }
            }
            builder.append(DOUBLE_TABLE).append("返回值: ").append(NEXT_LINE + DOUBLE_TABLE + TABLE)
                    .append(object.getReturnType()).append(NEXT_LINE + NEXT_LINE);
        }
        writer.write(builder.toString());
    }


    public static void generateFile(MessageBody messageBody, String registerID){
        File file = new File("F:\\stady_program\\项目备份\\file\\" + registerID);
        if(file.exists()) file.delete();
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(messageBody);
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
