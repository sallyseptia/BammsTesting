package com.example.asus.bamms;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.bamms.Interface.ItemClickListener;
import com.example.asus.bamms.Model.Transaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mutation extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    UserSessionManager session;

    String name2;
    String phone;

    FirebaseDatabase database;
    DatabaseReference transaction;

    MutationAdapter adapter;
    List<Transaction> transactionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutation);

        //Firebase
        database = FirebaseDatabase.getInstance();
        transaction = database.getReference("Transaction");

        //Session class instance
        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        name2 = user.get(UserSessionManager.KEY_NAME);
        phone = user.get(UserSessionManager.KEY_PHONE);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_mutation_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadListTransaction();

    }

    private void loadListTransaction()
    {
        transaction.orderByChild("userId").equalTo(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot transactionChild : dataSnapshot.getChildren()) {
                    final String transactionId = transactionChild.child("transactionId").getValue(String.class);
                    final String type = transactionChild.child("type").getValue(String.class);
                    final String name = transactionChild.child("name").getValue(String.class);
                    final String userId = transactionChild.child("userId").getValue(String.class);
                    final String date = transactionChild.child("date").getValue(String.class);
                    final Long amount = transactionChild.child("amount").getValue(Long.class);
                    final String receivernum = transactionChild.child("receivernum").getValue(String.class);

                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(transactionId);
                    transaction.setType(type);
                    transaction.setName(name);
                    transaction.setUserId(userId);
                    transaction.setDate(date);
                    transaction.setAmount(amount.intValue());
                    transaction.setReceivernum(receivernum);
                    transactionList.add(transaction);
                }

                adapter = new MutationAdapter(transactionList, getApplicationContext(), new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
