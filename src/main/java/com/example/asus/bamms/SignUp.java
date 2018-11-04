package com.example.asus.bamms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.bamms.Common.Common;
import com.example.asus.bamms.Model.Account;
import com.example.asus.bamms.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText edtPhone,edtName,edtPassword,edtConfirmPassword,edtEmail;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initialize Views
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtName = (EditText)findViewById(R.id.edtName);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText)findViewById(R.id.edtConfirmPassword);
        edtEmail =(EditText)findViewById(R.id.edtEmail);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        //Init Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        final DatabaseReference table_account = database.getReference("Account");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final String edt_phone = edtPhone.getText().toString();
                    String edt_Email = edtEmail.getText().toString();
                    String edt_name = edtName.getText().toString();
                    String password = edtPassword.getText().toString();
                    String confirmPassword = edtConfirmPassword.getText().toString();

                    if(edt_phone == "" || edt_phone.equals(""))
                    {
                        Toast.makeText(SignUp.this, "Please input your phone number!", Toast.LENGTH_SHORT).show();
                    }
                    else if (dataSnapshot.child(edt_phone).exists())
                    {
                        Toast.makeText(SignUp.this, "Phone Number already registered!", Toast.LENGTH_SHORT).show();
                    }
                    else if(edt_Email == "" || edt_Email.equals(""))
                    {
                        Toast.makeText(SignUp.this, "Please input your email!", Toast.LENGTH_SHORT).show();
                    }
                    else if(edt_name == "" || edt_name.equals(""))
                    {
                        Toast.makeText(SignUp.this, "Please input your name!", Toast.LENGTH_SHORT).show();
                    }
                    else if (password == "" || password.equals("")) {

                        Toast.makeText(SignUp.this, "Please input your password!", Toast.LENGTH_SHORT).show();
                    }
                    else if (confirmPassword == "" || confirmPassword.equals("")) {

                        Toast.makeText(SignUp.this, "Please input your confirm password!", Toast.LENGTH_SHORT).show();
                    }
                    else if (!password.equals(confirmPassword)) {

                        Toast.makeText(SignUp.this, "Password didn't match!", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        String NAME = edtName.getText().toString();
                        String PASSWORD = edtPassword.getText().toString();
                        String EMAIL = edtEmail.getText().toString();
                        String PHONE = edtPhone.getText().toString();
                        String accountNum = "00"+String.valueOf(System.currentTimeMillis());
                        String id = String.valueOf(System.currentTimeMillis());

                        //Input to firebase
                        User user = new User(PHONE,NAME,PASSWORD,EMAIL);
                        table_user.child(PHONE).setValue(user);

                        Account account = new Account(id,accountNum,"","tabungan",PHONE,0);
                        table_account.child(id).setValue(account);

                        Toast.makeText(SignUp.this, "Data uploaded,sign up success!", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(SignUp.this, LogInActivity.class);
                        Common.currentUser = user;
                        startActivity(loginIntent);
                        finish();
                        }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            }
        });
    }
}
