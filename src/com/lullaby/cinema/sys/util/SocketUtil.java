package com.lullaby.cinema.sys.util;

import com.lullaby.cinema.sys.message.Message;

import java.io.*;
import java.net.Socket;

/**
 * 套接字工具类
 */
public class SocketUtil {
    /**
     * IP地址
     */
    private static final String IP = "localhost";
    /**
     * 端口
     */
    private static final int PORT = 8888;

    /**
     * 向服务器端发送消息
     * @param msg 发送的消息
     * @return 接收的消息
     * @param <T> 因为不确定发送消息携带的数据类型，因此使用泛型
     * @param <V> 因为不确定接收消息使用的数据类型，因此使用泛型
     */
    public static <T, V> V sendMessage(Message<T> msg) {
        try {
            Socket client = new Socket(IP, PORT);
            // 获取输出流
            OutputStream outputStream = client.getOutputStream();
            // 将输出流包装为对象输出流
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            // 对象流写对象，也就是信息传输
            objectOutputStream.writeObject(msg);
            // 强制将通道中的信息刷出
            objectOutputStream.flush();
            // 告诉服务器端信息传输已经完毕
            client.shutdownOutput();

            // 获取输入流
            InputStream inputStream = client.getInputStream();
            // 将输入流包装为对象输入流
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            V result = (V) objectInputStream.readObject();
            // 告诉服务器端，信息读取已经完毕
            client.shutdownInput();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
