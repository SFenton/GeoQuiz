package com.sfenton.geoquiz;

/**
 * Created by sfent on 3/12/2018.
 */

public class Question {
    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    private int mTextResId;

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    private boolean mAnswerTrue;

    public boolean getHasBeenAnswered() {
        return mHasBeenAnswered;
    }

    public void setHasBeenAnswered(boolean hasBeenAnswered) {
        mHasBeenAnswered = hasBeenAnswered;
    }

    private boolean mHasBeenAnswered;

    public boolean isAnsweredCorrectly() {
        return mAnsweredCorrectly;
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly) {
        mAnsweredCorrectly = answeredCorrectly;
    }

    private boolean mAnsweredCorrectly;

    public Question(int textResId, boolean answerTrue)
    {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mHasBeenAnswered = false;
    }
}
