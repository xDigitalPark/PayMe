package apps.digitakpark.payme.password;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import apps.digitakpark.payme.MainActivity;
import payme.pe.apps.digitakpark.payme.R;

public class PasswordActivity extends AppCompatActivity implements PasswordView {

    SharedPreferences preferences;
    boolean passwordEnabled;
    String passwordValue;

    @BindView(R.id.password_activity_password_textview)
    EditText passwordActivityPasswordTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        passwordEnabled = preferences.getBoolean("password_enabled", false);
        passwordValue = preferences.getString("password_value", "");

        if (!passwordEnabled) {
            navigateToMainActivity();
        }
        super.onStart();
    }

    @OnClick(R.id.password_activity_validate_password)
    public void validatePassword() {
        String password = passwordActivityPasswordTextview.getText().toString();
        if (password.equals(passwordValue)) {
            onPasswordValidatedSuccess();
        } else {
            onPasswordValidatedFailed();
        }
    }


    public void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPasswordValidatedSuccess() {
        navigateToMainActivity();
    }

    @Override
    public void onPasswordValidatedFailed() {
        passwordActivityPasswordTextview.setError(getString(R.string.password_activity_wrong_password));
        passwordActivityPasswordTextview.setText("");
        passwordActivityPasswordTextview.setFocusable(true);
    }


}
