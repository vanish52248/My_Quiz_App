package com.example.quizapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Dao・・・Data Access Object
public class QuizFileDao {

    // 再代入できない
    // 定数 フィールドに定数書くときはstaticもセットで書く
    // javaの作法としてfinalの変数名は大文字とアンダースコアで区切る
    private static final String FILE_PATH = "quizzes.txt";

    public void wrire(List<Quiz> quizzes) throws IOException {
            List<String> lines =new ArrayList<>();
            for (Quiz quiz: quizzes){
                lines.add(quiz.toString());
            }

        Path path = Paths.get(FILE_PATH);

        // 第1引数・・・書き込み先のファイルパス（場所）
        // 第2引数・・・書き込むデータ List<String>
        Files.write(path, lines);
    }

    public List<Quiz> read() throws IOException {
        Path path =Paths.get(FILE_PATH);
        List<String> lines = Files.readAllLines(path); // 例外が発生すると throw 例外を投げる（飛ばす）

        List<Quiz> quizzes = new ArrayList<>();
        for (String line: lines){
            quizzes.add(Quiz.fromString(line));
        }
        return quizzes;
    }
}
