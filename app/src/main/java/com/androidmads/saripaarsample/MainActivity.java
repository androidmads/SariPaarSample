package com.androidmads.saripaarsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {

    @BindView(R.id.email)
    @NotEmpty
    @Email
    EditText emailEditText;

    @BindView(R.id.password)
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    EditText passwordEditText;

    @BindView(R.id.conf_password)
    @ConfirmPassword
    EditText confirmPasswordEditText;

    @BindView(R.id.phone)
    @Pattern(regex = "^[7-9][0-9]{9}$", message = "Invalid Mobile Number")
    EditText phoneEditText;

    @OnClick(R.id.submit)
    public void onButtonClick(View view) {
        validator.validate();
    }

    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize ButterKnife
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
