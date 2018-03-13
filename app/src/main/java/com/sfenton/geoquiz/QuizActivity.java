package com.sfenton.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mButtonFalse;
    private Button mButtonTrue;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String BUTTONS_ENABLED = "buttons_enabled";

    private Question[] mQuestionBank = new Question[] {
      new Question(R.string.question_awake, false),
      new Question(R.string.question_bep, true),
            new Question(R.string.question_ice, true),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mButtonTrue = findViewById(R.id.true_button);
        mButtonFalse = findViewById(R.id.false_button);

        if (savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mButtonTrue.setEnabled(savedInstanceState.getBoolean(BUTTONS_ENABLED, true));
            mButtonFalse.setEnabled(savedInstanceState.getBoolean(BUTTONS_ENABLED, true));
        }
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.prev_button);

        mButtonTrue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer(true);
            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex == 0)
                {
                    mCurrentIndex = mQuestionBank.length - 1;
                }
                else
                {
                    mCurrentIndex--;
                }
                updateQuestion();
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }

    private void updateQuestion()
    {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mButtonFalse.setEnabled(!mQuestionBank[mCurrentIndex].getHasBeenAnswered());
        mButtonTrue.setEnabled(!mQuestionBank[mCurrentIndex].getHasBeenAnswered());
    }

    private void checkAnswer(boolean answer)
    {
        if (answer == mQuestionBank[mCurrentIndex].isAnswerTrue())
        {
            Toast.makeText(QuizActivity.this,
                    R.string.correct_toast,
                    Toast.LENGTH_SHORT).show();
            mQuestionBank[mCurrentIndex].setAnsweredCorrectly(true);
            mQuestionBank[mCurrentIndex].setHasBeenAnswered(true);
        }
        else
        {
            Toast.makeText(QuizActivity.this,
                    R.string.incorrect_toast,
                    Toast.LENGTH_SHORT).show();

            mQuestionBank[mCurrentIndex].setAnsweredCorrectly(false);
            mQuestionBank[mCurrentIndex].setHasBeenAnswered(true);
        }

        int numRight = 0;
        for (int i = 0; i < mQuestionBank.length; i++)
        {
            if (!mQuestionBank[i].getHasBeenAnswered())
            {
                break;
            }

            if (mQuestionBank[i].isAnsweredCorrectly())
            {
                numRight++;
            }

            if (i == mQuestionBank.length - 1)
            {
                Toast.makeText(QuizActivity.this,
                        "Quiz Complete!  Percentage: " + (float)((float)numRight / (float)mQuestionBank.length) * 100.0f + "%",
                        Toast.LENGTH_LONG).show();
            }
        }

        mButtonTrue.setEnabled(false);
        mButtonFalse.setEnabled(false);
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState)
    {
        super.onSaveInstanceState(saveInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        saveInstanceState.putBoolean(BUTTONS_ENABLED, mButtonTrue.isEnabled());
    }
}
