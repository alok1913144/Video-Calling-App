package com.jpr.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class signUpActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText emailBox, passwordBox, nameBox;
    Button loginBtn, signupBtn;
    CheckBox checkBox;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        emailBox = findViewById(R.id.emailBox);
        nameBox = findViewById(R.id.namebox);
        passwordBox = findViewById(R.id.passwordBox);
        checkBox=findViewById(R.id.register_checkbox);

        loginBtn = findViewById(R.id.loginbtn);
        signupBtn = findViewById(R.id.createBtn);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b){
                    passwordBox.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //confirm_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    passwordBox.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //confirm_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass, name;
                email = emailBox.getText().toString();
                pass = passwordBox.getText().toString();
                name = nameBox.getText().toString();

                User user = new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass) || !TextUtils.isEmpty(name)){


                        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    database.collection("Users")
                                            .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            startActivity(new Intent(signUpActivity.this, LoginActivity.class));
                                        }
                                    });
                                    Toast.makeText(signUpActivity.this, "Account is created.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(signUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                else {
                    Toast.makeText(signUpActivity.this,"please fill all fields",Toast.LENGTH_SHORT).show();

                }


            }
        });
    }
}