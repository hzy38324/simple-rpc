package com.sexycode.simplerpc.reuqest;

import java.io.Serializable;

/**
 * <p>
 *
 * @author: hzy created on 2018/05/02
 */
public class CalculateRpcRequest implements Serializable {

    private static final long serialVersionUID = 7503710091945320739L;

    private String method;
    private int a;
    private int b;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "CalculateRpcRequest{" +
                "method='" + method + '\'' +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}
