package com.example.android.jassmusic;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link person_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class person_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView create_account_text,proof,upload_txt;
    Button login_button,logout_button;
    EditText login_email,login_password;
    FirebaseAuth mAuth;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment person_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static person_Fragment newInstance(String param1, String param2) {
        person_Fragment fragment = new person_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public person_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent=new Intent(getContext(),first_page.class);
//            startActivity(intent);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_, container, false);

        mAuth = FirebaseAuth.getInstance();
        create_account_text=view.findViewById(R.id.signin_text);
        login_button=view.findViewById(R.id.login_button);
        logout_button=view.findViewById(R.id.logout_button);
        login_email=view.findViewById(R.id.login_email);
        login_password=view.findViewById(R.id.login_password);
        proof=view.findViewById(R.id.proof);
        upload_txt=view.findViewById(R.id.upload_text);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(login_email.getText());
                password = String.valueOf(login_password.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Objects.requireNonNull(getContext()), "enter email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Objects.requireNonNull(getContext()), "enter password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(Objects.requireNonNull(getContext()), "Authentication successful.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getContext(),first_page.class);

                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Objects.requireNonNull(getContext()), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        create_account_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),signin_activity.class);
                startActivity(intent);
            }
        });
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getContext(),first_page.class);
                startActivity(intent);
            }
        });
        upload_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),upload_activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}