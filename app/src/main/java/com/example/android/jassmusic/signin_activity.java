package com.example.android.jassmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signin_activity extends AppCompatActivity {

    TextView login_text;
    EditText edit_email, edit_password,edit_name;
    Button signin_button;
//    int color = R.color.red;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),first_page.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();
        login_text = findViewById(R.id.login_text);
        edit_name=findViewById(R.id.signin_name);
        edit_email = findViewById(R.id.signin_email);
        edit_password = findViewById(R.id.signin_password);
        signin_button = findViewById(R.id.signin_button);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,email, password;
                name = String.valueOf(edit_name.getText());
                email = String.valueOf(edit_email.getText());
                password = String.valueOf(edit_password.getText());

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(signin_activity.this, "enter name",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(signin_activity.this, "enter email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(signin_activity.this, "enter password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    usermodel usermodel1 = new usermodel(name,email,password);
                                    firebaseDatabase=FirebaseDatabase.getInstance();
                                    databaseReference=firebaseDatabase.getReference("user");
                                    databaseReference.child(name).setValue(usermodel1);
                                    Toast.makeText(signin_activity.this, "Authentication successful.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(signin_activity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signin_activity.this, person_Fragment.class);
                startActivity(intent);
            }
        });


    }
}