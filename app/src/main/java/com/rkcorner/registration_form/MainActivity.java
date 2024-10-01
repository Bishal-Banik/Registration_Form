package com.rkcorner.registration_form;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etContact, etPassword;
    private Spinner spinnerProfession, spinnerDept;
    private Button btnSubmit;
    private CheckBox togglePasswordVisibility;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        etPassword = findViewById(R.id.etPassword);
        spinnerProfession = findViewById(R.id.spinnerProfession);
        spinnerDept = findViewById(R.id.spinnerDept);
        btnSubmit = findViewById(R.id.btnSubmit);
        togglePasswordVisibility = findViewById(R.id.togglePasswordVisibility);
        tvResult = findViewById(R.id.tvResult);

        // Populate dropdowns
        ArrayAdapter<CharSequence> professionAdapter = ArrayAdapter.createFromResource(this,
                R.array.profession_array, android.R.layout.simple_spinner_item);
        professionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfession.setAdapter(professionAdapter);
        ArrayAdapter<CharSequence> deptAdapter = ArrayAdapter.createFromResource(this,
                R.array.department_array, android.R.layout.simple_spinner_item);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDept.setAdapter(deptAdapter);

        // Toggle password visibility
        togglePasswordVisibility.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            etPassword.setSelection(etPassword.getText().length()); // Keep cursor at the end
        });

        // Submit button click
        btnSubmit.setOnClickListener(v -> validateInput());
    }

    private void validateInput() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String profession = spinnerProfession.getSelectedItem().toString();
        String department = spinnerDept.getSelectedItem().toString();

        if (TextUtils.isEmpty(username)) {
            showToast("Please enter a username");
            return;
        }

        // Validate email (custom domain)
        if (!email.matches("^cse_0182210012101\\d{3}@lus\\.ac\\.bd$")) {
            showToast("Invalid email format");
            return;
        }

        // Validate contact number (must be 10-15 digits)
        if (!contact.matches("\\d{10,15}")) {
            showToast("Invalid contact number");
            return;
        }

        // Validate password length
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$")) {
            showToast("Password must have at least 8 characters, 1 uppercase, 1 lowercase, 1 number, and 1 special character.");
            return;
        }
        if (profession.equals("Choose Profession")) {
            showToast("Please select a profession");
            return;
        }

        // Validate department selection (ensure user selects an option other than the default)
        if (department.equals("Choose Department")) {
            showToast("Please select a department");
            return;
        }

        // Display submitted information in the TextView
        tvResult.setText("Username: " + username + "\nEmail: " + email + "\nContact: " + contact + "\nProfession: " + profession + "\nDepartment: " + department);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}