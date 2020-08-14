package com.example.quizapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// API・・・Application Programming Interface
@RestController
public class QuizApiController {
    // クラス Quiz
    private List<Quiz> quizzes = new ArrayList<>();
    private QuizFileDao quizFileDao = new QuizFileDao();

        @GetMapping("/quiz")
            public Quiz quiz(){
            int index = new Random().nextInt(quizzes.size()); // 引数が3の場合 0～2 乱数

            return quizzes.get(index);
        }

    // showメソッド
    // 戻り値 List<Quiz>型
    // 引数はない
    @GetMapping("/show")
    public List<Quiz> show() {
        return quizzes;
    }

    // createメソッド
    // 戻り値はない
    // 引数はString型のquestion,boolean型のanswer(問題の正解)
    @PostMapping("/create")
    public void create(@RequestParam String question, @RequestParam boolean answer){
        Quiz quiz = new Quiz(question, answer);
        quizzes.add(quiz);
    }

    // checkメソッド
    // 引数はString型のquestion(質問文), boolean型のanswer(回答)
    // 戻り値 正解・不正解 文字列で返却
    @GetMapping("/check")
    public String check(@RequestParam String question, boolean answer){
//      //回答が正しいかどうかチェックして、結果を返却する
        // 指定されたquestionを登録済みのクイズから検索する
        for (Quiz quiz:quizzes) {
            // もしクイズが見つかったら
            if (quiz.getQuestion().equals(question)){
                // 登録されているanswerと回答として渡ってきたanswerが一致している場合
                if (quiz.isAnswer() == answer){
                //「正解」と返却する
                    return "正解！";
                } else {
                    // もし一致していなければ「不正解」と返却する
                    return "不正解！";
                }

            }
        }
        //クイズが見つからなかった場合は、「問題がありません」と返却する
        return "問題がありません";
    }

    @PostMapping("/save")
    public String save() {
        try {
            quizFileDao.wrire(quizzes);
            return "ファイルに保存しました";
        } catch (IOException e) {
            e.printStackTrace();
            return "ファイルの保存に失敗しました";
        }
    }

    @GetMapping("/load")
    public String load() {
        try {
            quizzes = quizFileDao.read();
            return "ファイルを読み込みました";
        } catch (IOException e) {  //例外
            e.printStackTrace();
            return "ファイルの読み込みに失敗しました";
        }
    }
}
