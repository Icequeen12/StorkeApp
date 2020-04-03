package com.example.pamela.udar.extras;

public class FunctionalUpload {

    public String url;
    public String right;
    public String a;
    public String b;
    public  String c;
    public String task;


    public FunctionalUpload(String url, String right, String a, String b, String c, String task) {
        this.url = url;
        this.right = right;
        this.a = a;
        this.b = b;
        this.c = c;
        this.task = task;
    }

    public FunctionalUpload() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}