package com.example.quizapp;

// Object
public class Quiz {

    /**
     * 問題文
     */
    private String question;

    /**
     * クイズの正解
     * 〇・・・true
     * ✕・・・false
     */
    private boolean answer;

    // コンストラクタ
    public  Quiz(String question, boolean answer){
        this.question = question;
        this.answer = answer;

    }

    public String getQuestion() {
        return question;
    }

    public boolean isAnswer(){
        return answer;
    }

    // JUnit
    @Override
    public String toString(){
        // 条件演算子
        String marubatu = answer ? "〇" : "×";
//        String marubatu;
//        if(answer){
//            marubatu = "〇";
//        } else {
//            marubatu = "×";
//        }
        return question + " " + marubatu;
    }

    // line・・・
    // 問題文 〇  lengthは５ -2 endIndex 3
    // もんだいぶん１ × lengthは９ -2 endIndex 7
    public static Quiz fromString(String line) {
        String question = line.substring(0,line.length() - 2);
        boolean answer = line.endsWith("〇");

        return new Quiz(question, answer);
    }
}

