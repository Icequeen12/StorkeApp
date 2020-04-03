package com.example.pamela.udar.extras;

public class InstructionsUpload {

    private String quote1;
    private String quote2;
    private String quote3;
    private String quote4;
    private String right;

    public InstructionsUpload() {
    }

    public InstructionsUpload(String quote1, String quote2, String quote3, String quote4, String right) {
        this.quote1 = quote1;
        this.quote2 = quote2;
        this.quote3 = quote3;
        this.quote4 = quote4;
        this.right = right;
    }

    public String getQuote1() {
        return quote1;
    }

    public void setQuote1(String quote1) {
        this.quote1 = quote1;
    }

    public String getQuote2() {
        return quote2;
    }

    public void setQuote2(String quote2) {
        this.quote2 = quote2;
    }

    public String getQuote3() {
        return quote3;
    }

    public void setQuote3(String quote3) {
        this.quote3 = quote3;
    }

    public String getQuote4() {
        return quote4;
    }

    public void setQuote4(String quote4) {
        this.quote4 = quote4;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}
