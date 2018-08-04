package com.example.shaad.snpppapp.Utility;

/**
 * Created by Shaad on 19-03-2018.
 */

public class FAQ {
    private String _question;
    private String _answer;

    public FAQ() {
    }

    public FAQ(String _question, String _answer) {
        this._question = _question;
        this._answer = _answer;
    }

    public String get_question() {
        return _question;
    }

    public void set_question(String _question) {
        this._question = _question;
    }

    public String get_answer() {
        return _answer;
    }

    public void set_answer(String _answer) {
        this._answer = _answer;
    }
}
