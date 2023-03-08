package com.example.annotaionmessage.launch;

import com.example.annotaionmessage.entity.*;
import com.example.annotaionmessage.service.ClassMethodsRequestScanner;
import com.example.annotaionmessage.service.MessageServerConnection;
import com.example.annotaionmessage.service.Scanner;
import com.example.annotaionmessage.utils.ParseMessageGenerateFile;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.AnnotationMetadata;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Properties;
import java.util.Timer;

public class AnnotationScannerSelector implements ImportSelector {
    private static final String BINARY_PATH = "\\target\\classes";
    private static final String PROPERTY_PATH = "\\src\\main\\resources\\application.properties";
    private static final String YAMl_FILE = "application.yaml";
    private static final String ROOT_PATH = System.getProperty("user.dir");
    private static Properties properties = null;

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Scanner scanner = new ClassMethodsRequestScanner();
        LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list = new LinkedList<>();
        properties = getProjectProperties();
        launch(new File(ROOT_PATH + BINARY_PATH), "", scanner, list);
        if (list.size() > 0) sendMessage(list);
        return new String[0];
    }

    private void launch(File file, String path, Scanner scanner,
                        LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list) {
        if (!file.exists()) {
            throw new RuntimeException(file.getName() + "is not exits");
        }
        for (File fileItem : Objects.requireNonNull(file.listFiles())) {
            String fileName = fileItem.getName();
            if (fileName.endsWith(".class")) {
                String classPath = path + "." + fileName.substring(0, fileName.lastIndexOf("."));
                Entry<Entry<String[], String>, LinkedList<MessageObject>> content = loadClass(classPath, scanner);
                if (content != null) list.add(content);
            }
            if (fileItem.isDirectory()) launch(fileItem, path + (path.equals("") ? "" : ".") + fileName, scanner, list);
        }
    }


    private Entry<Entry<String[], String>, LinkedList<MessageObject>> loadClass(String path, Scanner scanner) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(path);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return scanner.scanRequestAnnotation(clazz);
    }


    private void sendMessage(LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list) {
        MessageServerConnection messageServerConnection = readApplicationFile();
        if (messageServerConnection.getAddress().equals("127.0.0.1") || messageServerConnection.getAddress().equals("localhost")) {
            ParseMessageGenerateFile.generatorFile(list);
            return;
        }
        String registerID = properties.getProperty("message.register-id");
        if (registerID == null)
            throw new RuntimeException("请先获取注册码，未获取注册码的情况下上传项目可能会被打回");
        Socket clientSocket = null;
        try{
            clientSocket = new Socket(messageServerConnection.getAddress(), messageServerConnection.getPort());
            TransformBody transformBody = new TransformBody(registerID, generateMessageBody(list));
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new Task(clientSocket, transformBody), 100, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private MessageBody generateMessageBody(LinkedList<Entry<Entry<String[], String>, LinkedList<MessageObject>>> list) {
        if (properties == null) {
            return new MessageBody("", 8080, list);
        }
        String port = properties.getProperty("server.port"),
                contextPath = properties.getProperty("server.servlet.context-path");
        if (contextPath == null) contextPath = "";
        if (port == null) port = "8080";
        return new MessageBody(contextPath, Integer.parseInt(port), list);
    }


    private MessageServerConnection readApplicationFile() {
        if (properties == null){
            return new MessageServerConnection();
        }
        String address = properties.getProperty("message.address");
        String port = properties.getProperty("message.port");
        MessageServerConnection messageServerConnection = new MessageServerConnection();
        if (address != null && !address.equals("") && port != null && !port.equals("")) {
            messageServerConnection.setAddress(address);
            try {
                messageServerConnection.setPort(Integer.parseInt(port));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("端口号不是数组，port=" + port);
            }
        }
        return messageServerConnection;
    }


    private Properties getProjectProperties() {
        Properties properties = new Properties();
        File property = new File(ROOT_PATH + PROPERTY_PATH);
        try {
            if (property.exists()) {
                properties.load(new FileInputStream(property));
            } else {
                property = new File(ROOT_PATH + "\\src\\main\\resources\\" + YAMl_FILE);
                if (!property.exists()) {
                    return null;
                }
                YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
                yamlPropertiesFactoryBean.setResources(new ClassPathResource(YAMl_FILE));
                properties = yamlPropertiesFactoryBean.getObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }



}
