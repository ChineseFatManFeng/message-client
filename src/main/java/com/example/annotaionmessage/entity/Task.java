package com.example.annotaionmessage.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.TimerTask;

/**
 * Created by Time Treval
 * 2023/3/7 13:26
 **/
public class Task extends TimerTask {
    private final Socket socket;
    private final String transformBodyText;
    private boolean sendFlag = true;


    public Task(Socket socket, TransformBody transformBody){
        this.socket = socket;
        this.transformBodyText = JSON.toJSONString(transformBody);
    }


    @Override
    public void run() {
        try{
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(JSON.toJSONString(new Notice(1)));
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            String content = "-1";
            while(content.equals("-1")){
                 content = inputStream.readUTF();
            }
            Notice notice = JSONObject.parseObject(content, Notice.class);
            if(notice.getFlag() == 2 && sendFlag) {
                outputStream.writeUTF(transformBodyText);
                sendFlag = false;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
