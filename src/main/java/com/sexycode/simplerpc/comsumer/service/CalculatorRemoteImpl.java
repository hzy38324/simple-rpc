package com.sexycode.simplerpc.comsumer.service;

import com.sexycode.simplerpc.provider.service.Calculator;
import com.sexycode.simplerpc.reuqest.CalculateRpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * @author: hzy created on 2018/05/02
 */
public class CalculatorRemoteImpl implements Calculator {
    public static final int PORT = 9090;
    private static Logger log = LoggerFactory.getLogger(CalculatorRemoteImpl.class);

    public int add(int a, int b) {
        List<String> addressList = lookupProviders("Calculator.add");
        String address = chooseTarget(addressList);
        try {
            Socket socket = new Socket(address, PORT);

            // 将请求序列化
            CalculateRpcRequest calculateRpcRequest = generateRequest(a, b);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // 将请求发给服务提供方
            objectOutputStream.writeObject(calculateRpcRequest);

            // 将响应体反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object response = objectInputStream.readObject();

            log.info("response is {}", response);

            if (response instanceof Integer) {
                return (Integer) response;
            } else {
                throw new InternalError();
            }

        } catch (Exception e) {
            log.error("fail", e);
            throw new InternalError();
        }
    }

    private CalculateRpcRequest generateRequest(int a, int b) {
        CalculateRpcRequest calculateRpcRequest = new CalculateRpcRequest();
        calculateRpcRequest.setA(a);
        calculateRpcRequest.setB(b);
        calculateRpcRequest.setMethod("add");
        return calculateRpcRequest;
    }

    private String chooseTarget(List<String> providers) {
        if (null == providers || providers.size() == 0) {
            throw new IllegalArgumentException();
        }
        return providers.get(0);
    }

    public static List<String> lookupProviders(String name) {
        List<String> strings = new ArrayList();
        strings.add("127.0.0.1");
        return strings;
    }
}
