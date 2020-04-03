package com.example.pamela.udar.extras;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class OneOddUpload {

    public String first;
    public String second;
    public String third;
    public String fourth;
    public String right;

    public OneOddUpload() {
    }

    public OneOddUpload(String first, String second, String third, String fourth, String right) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.right = right;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getFourth() {
        return fourth;
    }

    public void setFourth(String fourth) {
        this.fourth = fourth;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}
