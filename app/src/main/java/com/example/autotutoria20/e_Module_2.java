package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class e_Module_2 {

    static e_Question[] getPreTestQuestions(String[] questions, String[][] choices, int[] answers) {
        List<e_Question> questionList = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            questionList.add(new e_Question(questions[i], Arrays.asList(choices[i]), answers[i])); // Pre-test is always multiple choice
        }

        // Shuffle the list to randomize the order of the questions
        Collections.shuffle(questionList);

        // Convert the list back to an array
        return questionList.toArray(new e_Question[0]);
    }

    static e_Question[] get_PostTest_EasyMedium_Questions(String[] questions, String[][] choices, int[] answers, e_Question.Difficulty level) {
        List<e_Question> questionList = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            questionList.add(new e_Question(questions[i], Arrays.asList(choices[i]), answers[i], level)); // Adjust based on difficulty
        }

        // Shuffle the list to randomize the order of the questions
        Collections.shuffle(questionList);

        // Convert the list back to an array
        return questionList.toArray(new e_Question[0]);
    }

    static e_Question[] get_PostTest_Hard_Questions(String[] questions, String[] answers, e_Question.Difficulty level) {
        List<e_Question> questionList = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            questionList.add(new e_Question(questions[i], answers[i], level)); // Adjust based on difficulty
        }

        // Shuffle the list to randomize the order of the questions
        Collections.shuffle(questionList);

        // Convert the list back to an array
        return questionList.toArray(new e_Question[0]);
    }

    static int getPreTestCount(String module) {
        if (module.equals("M1")) {
            return e_Module_2_1.pre_test_lesson_1_answers.length;
        }
        return 0;
    }

}
