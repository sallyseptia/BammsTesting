package com.example.asus.bamms;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.bamms.Model.Transaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Transfer extends AppCompatActivity {
    EditText edtAmount;
    EditText edtTransfer;
    Button btnTransfer;
    TextView txtAmount;
    String name2;
    String phone;
    FirebaseDatabase database;
    DatabaseReference account;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        database = FirebaseDatabase.getInstance();
        edtAmount = (EditText)findViewById(R.id.edtAmountTransfer);
        edtTransfer = (EditText)findViewById(R.id.edtTransfer);
        btnTransfer  = (Button)findViewById(R.id.btnSubmit);
        txtAmount = (TextView) findViewById(R.id.txtAmountTransfer);
        account = database.getReference("Account");

        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        name2 = user.get(UserSessionManager.KEY_NAME);
        phone = user.get(UserSessionManager.KEY_PHONE);

        //Init Firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_account= database.getReference("Account");
        final DatabaseReference table_account_destination= database.getReference("Account");
        final DatabaseReference table_transaction= database.getReference("Transaction");

        table_account.orderByChild("userId").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    for (final DataSnapshot accountChild : dataSnapshot.getChildren()) {
                        final String accountnumber = accountChild.child("accountNumber").getValue().toString();
                        final String accountId = accountChild.child("accountId").getValue().toString();
                        final String amount = accountChild.child("amount").getValue().toString();

                        txtAmount.setText("Total Amount: " + amount + " Rupiah");

                        btnTransfer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String accNum = edtTransfer.getText().toString();
                            try {
                                int accnumint= Integer.parseInt(accNum);
                                if (accNum.equals("")|| accNum.equals("Input Number")||accnumint<1) {
                                    Toast.makeText(Transfer.this, "Please input Number!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    table_account_destination.orderByChild("accountNumber").equalTo(accNum).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (final DataSnapshot accountDestinationChild : dataSnapshot.getChildren()) {
                                                String AMOUNT = edtAmount.getText().toString();
                                                Log.v("test123", accNum.equals(accountnumber) + "");
                                                if (!dataSnapshot.exists()) {
                                                    Toast.makeText(Transfer.this, "Account number not exists!", Toast.LENGTH_SHORT).show();
                                                } else if (accNum.equals(accountnumber)) {
                                                    Toast.makeText(Transfer.this, "Cannot transfer to same account!", Toast.LENGTH_SHORT).show();
                                                } else if (AMOUNT.equals("") || Integer.parseInt(AMOUNT) < 1 || AMOUNT.equals("Input Amount")) {
                                                    Toast.makeText(Transfer.this, "Please input amount!", Toast.LENGTH_SHORT).show();
                                                } else if (Integer.parseInt(AMOUNT) > Integer.parseInt(amount)) {
                                                    Toast.makeText(Transfer.this, "Not enough deposit to transfer!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    final String accountDestinationId = accountDestinationChild.child("accountId").getValue().toString();
                                                    final String amountDestination = accountDestinationChild.child("amount").getValue().toString();

                                                    String id = String.valueOf(System.currentTimeMillis());
                                                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
                                                    Transaction transaction = new Transaction(id, "Transfer", name2, phone, timeStamp, Integer.parseInt(AMOUNT), accNum);

                                                    table_transaction.child(id).setValue(transaction);
                                                    table_account.child(accountId).child("amount").setValue(Integer.parseInt(amount) - Integer.parseInt(AMOUNT));

                                                    table_account_destination.child(accountDestinationId).child("amount").setValue(Integer.parseInt(amountDestination) + Integer.parseInt(AMOUNT));
                                                    Toast.makeText(Transfer.this, "Transfer Success", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }catch (NumberFormatException e){
                                System.out.println("not a number");
                            }


                            }
                        });
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
