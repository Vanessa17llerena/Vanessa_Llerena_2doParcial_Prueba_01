package com.aperez.apps.eventhandlers;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.widget.Toast;

import com.aperez.apps.androidfunwithflags.VELLC_MainActivity;
import com.aperez.apps.androidfunwithflags.R;

import java.util.Set;

public class PreferenceChangeListener implements OnSharedPreferenceChangeListener {
    private VELLC_MainActivity VELLCMainActivity;

    public PreferenceChangeListener(VELLC_MainActivity VELLCMainActivity) {
        this.VELLCMainActivity = VELLCMainActivity;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        this.VELLCMainActivity.setPreferencesChanged(true);

        if (key.equals(this.VELLCMainActivity.getREGIONS())) {
            this.VELLCMainActivity.getQuizViewModel().setGuessRows(sharedPreferences.getString(
                    VELLC_MainActivity.CHOICES, null));
            this.VELLCMainActivity.getQuizFragment().resetQuiz();
        } else if (key.equals(this.VELLCMainActivity.getCHOICES())) {
            Set<String> regions = sharedPreferences.getStringSet(this.VELLCMainActivity.getREGIONS(),
                    null);
            if (regions != null && regions.size() > 0) {
                this.VELLCMainActivity.getQuizViewModel().setRegionsSet(regions);
                this.VELLCMainActivity.getQuizFragment().resetQuiz();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                regions.add(this.VELLCMainActivity.getString(R.string.default_region));
                editor.putStringSet(this.VELLCMainActivity.getREGIONS(), regions);
                editor.apply();
                Toast.makeText(this.VELLCMainActivity, R.string.default_region_message,
                        Toast.LENGTH_LONG).show();
            }
        }

        Toast.makeText(this.VELLCMainActivity, R.string.restarting_quiz,
                Toast.LENGTH_SHORT).show();
    }
}
