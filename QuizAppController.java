package com.example.quizapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("page")
public class QuizAppController {
    // クラス Quiz
    private List<Quiz> quizzes = new ArrayList<>();
    private QuizFileDao quizFileDao = new QuizFileDao();

        @GetMapping("/quiz")
            public String quiz(Model model){
            int index = new Random().nextInt(quizzes.size()); // 引数が3の場合 0～2 乱数

            model.addAttribute("quiz",quizzes.get(index));
            return "quiz";
        }

    // showメソッド
    @GetMapping("/show")
    public String show(Model model) {
            model.addAttribute("quizzes",quizzes);
        return "list";
    }

    // createメソッド
    // 引数はString型のquestion,boolean型のanswer(問題の正解)
    @PostMapping("/create")
    public String create(@RequestParam String question, @RequestParam boolean answer){
        Quiz quiz = new Quiz(question, answer);
        quizzes.add(quiz);

        return "redirect:/page/show";
    }

    // checkメソッド
    @GetMapping("/check")
    public String check(Model model, @RequestParam String question, boolean answer){
//      //回答が正しいかどうかチェックして、結果を返却する
        // 指定されたquestionを登録済みのクイズから検索する
        for (Quiz quiz:quizzes) {
            // もしクイズが見つかったら
            if (quiz.getQuestion().equals(question)){
                model.addAttribute("quiz",quiz);
                // 登録されているanswerと回答として渡ってきたanswerが一致している場合
                if (quiz.isAnswer() == answer){
                //「正解」とセットする
                    model.addAttribute("result","正解！");
                } else {
                    // もし一致していなければ「不正解」とセットする
                    model.addAttribute("result","不正解！");
                }
            }
        }
        return "answer";
    }

    @PostMapping("/save")
    public String save(RedirectAttributes attributes) {
        try {
            quizFileDao.wrire(quizzes);
            attributes.addFlashAttribute("successMessage","ファイルに保存しました");
        } catch (IOException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage","ファイルの保存に失敗しました");
        }

        return "redirect:/page/show";
    }

    @GetMapping("/load")
    public String load(RedirectAttributes attributes) {
        try {
            quizzes = quizFileDao.read();
            attributes.addFlashAttribute("successMessage","ファイルを読み込みました");
        } catch (IOException e) {  //例外
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage","ファイルの読み込みに失敗しました");
        }

        return "redirect:/page/show";
    }
}
