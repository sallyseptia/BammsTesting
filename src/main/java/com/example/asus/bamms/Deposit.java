package com.example.asus.bamms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.bamms.Common.Common;
import com.example.asus.bamms.Model.Account;
import com.example.asus.bamms.Model.Transaction;
import com.example.asus.bamms.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Deposit extends AppCompatActivity {

    EditText edtAmount;
    Button btnWithdraw;
    Button btnDeposit;
    TextView txtAmount;
    String name2;
    String phone;
    FirebaseDatabase database;
    DatabaseReference account;

    //User session manager class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        database = FirebaseDatabase.getInstance();
        edtAmount = (EditText)findViewById(R.id.edtAmount1);
        btnDeposit = (Button)findViewById(R.id.btnDeposit);
        btnWithdraw  = (Button)findViewById(R.id.btnWithdraw);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        account = database.getReference("Account");

        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        name2 = user.get(UserSessionManager.KEY_NAME);
        phone = user.get(UserSessionManager.KEY_PHONE);

        //Init Firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_account= database.getReference("Account");
        final DatabaseReference table_transaction= database.getReference("Transaction");
        table_account.orderByChild("userId").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot accountChild : dataSnapshot.getChildren()) {
                    final String accountId = accountChild.child("accountId").getValue().toString();
                    final String amount = accountChild.child("amount").getValue().toString();

                    txtAmount.setText("Total Amount: " + amount + " Rupiah");
                    btnDeposit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String AMOUNT = edtAmount.getText().toString();
                            if (AMOUNT.equals("")||Integer.parseInt(AMOUNT)<1||AMOUNT.equals("Input Amount")) {
                                Toast.makeText(Deposit.this, "Please input amount!", Toast.LENGTH_SHORT).show();
                            } else {
                                String id = String.valueOf(System.currentTimeMillis());
                                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
                                Transaction transaction = new Transaction(id,"Deposit",name2,phone,timeStamp,Integer.parseInt(AMOUNT),"");

                                table_transaction.child(id).setValue(transaction);
                                table_account.child(accountId).child("amount").setValue(Integer.parseInt(amount) + Integer.parseInt(AMOUNT));
                                Toast.makeText(Deposit.this, "Deposit Success", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });

                    btnWithdraw.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String AMOUNT = edtAmount.getText().toString();
                            if (AMOUNT.equals("")||Integer.parseInt(AMOUNT)<1||AMOUNT.equals("Input Amount")) {
                                Toast.makeText(Deposit.this, "Please input amount!", Toast.LENGTH_SHORT).show();
                            }
                            else if (Integer.parseInt(AMOUNT)>Integer.parseInt(amount)) {
                                Toast.makeText(Deposit.this, "Not enough deposit to withdraw!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String id = String.valueOf(System.currentTimeMillis());
                                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
                                Transaction transaction = new Transaction(id,"Withdraw",name2,phone,timeStamp,Integer.parseInt(AMOUNT),"");

                                table_transaction.child(id).setValue(transaction);

                                table_account.child(accountId).child("amount").setValue(Integer.parseInt(amount) - Integer.parseInt(AMOUNT));
                                Toast.makeText(Deposit.this, "Withdraw Success", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


    }

    });
    }
}

