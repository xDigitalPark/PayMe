package payme.apps.seven.org.payme.preferences;

import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import payme.apps.seven.org.payme.R;

public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
//        setupActionBar();
//        setContentView(R.layout.activity_preferences);
    }

//    private void setupActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            // Show the Up button in the action bar.
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    public ActionBar getSupportActionBar() {
//        return getSupportActionBar();
//    }
//
//    public void setSupportActionBar(@Nullable Toolbar toolbar) {
//        setSupportActionBar(toolbar);
//    }
}
