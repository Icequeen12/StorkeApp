package com.example.pamela.udar.extras;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class PatternUpload {

    public int answer;
    public String pattern;

    public PatternUpload() {
    }

    public PatternUpload(int answer, String pattern) {
        this.answer = answer;
        this.pattern = pattern;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
