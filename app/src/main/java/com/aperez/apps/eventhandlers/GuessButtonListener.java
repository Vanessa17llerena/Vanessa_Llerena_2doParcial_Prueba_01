package com.aperez.apps.eventhandlers;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aperez.apps.androidfunwithflags.VELLC_MainActivityFragment;
import com.aperez.apps.androidfunwithflags.R;
import com.aperez.apps.androidfunwithflags.VELLC_ResultsDialogFragment;
import com.aperez.apps.lifecyclehelpers.QuizViewModel;

public class GuessButtonListener implements OnClickListener {
    private VELLC_MainActivityFragment VELLCMainActivityFragment;
    private Handler handler;

    public GuessButtonListener(VELLC_MainActivityFragment VELLCMainActivityFragment) {
        this.VELLCMainActivityFragment = VELLCMainActivityFragment;
        this.handler = new Handler();
    }

    @Override
    public void onClick(View v) {
        Button guessButton = ((Button) v);
        String guess = guessButton.getText().toString();
        String answer = this.VELLCMainActivityFragment.getQuizViewModel().getCorrectCountryName();
        this.VELLCMainActivityFragment.getQuizViewModel().setTotalGuesses(1);

        if (guess.equals(answer)) {
            this.VELLCMainActivityFragment.getQuizViewModel().setCorrectAnswers(1);
            this.VELLCMainActivityFragment.getAnswerTextView().setText(answer + "!");
            this.VELLCMainActivityFragment.getAnswerTextView().setTextColor(
                    this.VELLCMainActivityFragment.getResources().getColor(R.color.correct_answer));

            this.VELLCMainActivityFragment.disableButtons();

            if (this.VELLCMainActivityFragment.getQuizViewModel().getCorrectAnswers()
                    == QuizViewModel.getFlagsInQuiz()) {
                VELLC_ResultsDialogFragment quizResults = new VELLC_ResultsDialogFragment();
                quizResults.setCancelable(false);
                try {
                    quizResults.show(this.VELLCMainActivityFragment.getChildFragmentManager(), "Quiz Results");
                } catch (NullPointerException e) {
                    Log.e(QuizViewModel.getTag(),
                            "GuessButtonListener: this.VELLCMainActivityFragment.getFragmentManager() " +
                                    "returned null",
                            e);
                }
            } else {
                this.handler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                VELLCMainActivityFragment.animate(true);
                            }
                        }, 2000);
            }
        } else {
            this.VELLCMainActivityFragment.incorrectAnswerAnimation();
            guessButton.setEnabled(false);
        }
    }
}
