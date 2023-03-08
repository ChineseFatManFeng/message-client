package com.example.annotaionmessage.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Time Treval
 * 2023/3/7 13:19
 **/
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    private int flag;
    private LocalDateTime sendTime;

    public Notice(int flag){
        this.flag = flag;
        this.sendTime = LocalDateTime.now();
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "flag=" + flag +
                ", sendTime=" + sendTime +
                '}';
    }
}
