package com.example.pamela.udar;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Upload{

    public int answer;
    public String url;

    public Upload(int answer, String url) {
        this.answer = answer;
        this.url = url;
    }

    public Upload() {
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}