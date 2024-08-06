package com.example.autotutoria20;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class z_Lesson_steps
{


//    MODULE 1- Introduction to Formal Languages and Automata
//    Lesson 1- Alphabets and strings
//    Lesson 2- Operations on strings
//    Lesson 3- Definition of a formal language
//    Lesson 4- Overview of automata theory
//
//    MODULE 2- Regular Languages and Finite Automata
//    Lesson 1- Regular languages and expressions
//
//    MODULE 3- Limitations of Regular Languages
//    Lesson 1- Pumping lemma for regular languages
//    Lesson 2- Examples of non-regular languages
//    Lesson 3- Introduction to context-free languages
//
//    MODULE 4- Introduction to Context-Free Grammars (CFG)
//    Lesson 1- Definition of CFG
//    Lesson 2- Components: terminals, non-terminals, start symbol, production rules
//    Lesson 3- Examples of CFGs for simple languages
//
//    MODULE 5- Derivation and Parse Trees
//    Lesson 1- Parse Trees and It’s Construction
//    Lesson 2- Relationship between derivation and parse trees
//    Lesson 3- Leftmost and Rightmost Derivations
//
//    MODULE 6- Ambiguity in Context Free-Grammar
//    Lesson 1- Ambiguous Grammar
//    Lesson 2- Unambiguous Grammar
//    Lesson 3- Inherent Ambiguity
//
//    MODULE 7- Normal Forms of CFGs
//    Lesson 1- Chomsky Normal Form (CNF)
//
//    MODULE 8- Parsing Algorithms for CFGs
//    Lesson 1- Top-down parsing (recursive descent, LL(1) parsing)
//    Lesson 2- Bottom-up parsing (shift-reduce, LR parsing)
//    Lesson 3- CYK algorithm for parsing in CNF


    //4,1,2,3

    public static final int[] steps_per_module = {4, 1, 3, 3, 3, 3, 1, 3};

    public static final int total_module_count = 8; // Kung ilang cards sa main menu
    public static final int[] lesson_1_steps = {5, 5, 5, 5}; // Kung ilang cards sa Lesson 1
    public static final int[] lesson_2_steps = {5}; // Kung ilang cards sa Lesson 2
    public static final int[] lesson_3_steps = {5, 5, 5}; // Kung ilang cards sa Lesson 3
    public static final int[] lesson_4_steps = {5, 5, 5}; // Kung ilang cards sa Lesson 4
    public static final int[] lesson_5_steps = {5, 5, 5}; // Kung ilang cards sa Lesson 5
    public static final int[] lesson_6_steps = {5, 5, 5}; // Kung ilang cards sa Lesson 6
    public static final int[] lesson_7_steps = {5}; // Kung ilang cards sa Lesson 7
    public static final int[] lesson_8_steps = {5, 5, 5}; // Kung ilang cards sa Lesson 8

    // Private constructor to prevent instantiation
    private z_Lesson_steps() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static int[] getLessonSteps(int lessonNumber) {
        switch (lessonNumber) {
            case 1:
                return lesson_1_steps;
            case 2:
                return lesson_2_steps;
            case 3:
                return lesson_3_steps;
            case 4:
                return lesson_4_steps;
            case 5:
                return lesson_5_steps;
            case 6:
                return lesson_6_steps;
            case 7:
                return lesson_7_steps;
            case 8:
                return lesson_8_steps;
            default:
                return new int[0];
        }
    }

    public static int[] getAllLessonSteps() {
        List<Integer> allSteps = new ArrayList<>();
        for (int step : lesson_1_steps) {
            allSteps.add(step);
        }
        for (int step : lesson_2_steps) {
            allSteps.add(step);
        }
        for (int step : lesson_3_steps) {
            allSteps.add(step);
        }
        for (int step : lesson_4_steps) {
            allSteps.add(step);
        }
        for (int step : lesson_5_steps) {
            allSteps.add(step);
        }
        for (int step : lesson_6_steps) {
            allSteps.add(step);
        }
        for (int step : lesson_7_steps) {
            allSteps.add(step);
        }
        for (int step : lesson_8_steps) {
            allSteps.add(step);
        }
        int[] result = new int[allSteps.size()];
        for (int i = 0; i < allSteps.size(); i++) {
            result[i] = allSteps.get(i);
            Log.e("getAllLessonSteps", "Step Count: " + i);
        }
        return result;
    }
}
