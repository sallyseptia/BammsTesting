package com.example.asus.bamms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.bamms.Model.Account;
import com.example.asus.bamms.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView txtFullName;
    TextView txtName;
    TextView txtAccountNum;
    TextView txtAmount;
    String name2;
    String phone;
    Account currentAccount;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new UserSessionManager(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HashMap<String, String> user = session.getUserDetails();
        name2 = user.get(UserSessionManager.KEY_NAME);
        phone = user.get(UserSessionManager.KEY_PHONE);

        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);
        txtName = (TextView)findViewById(R.id.edtname);
        txtAccountNum = (TextView)findViewById(R.id.edtaccountNum);
        txtAmount = (TextView)findViewById(R.id.edtAmount);

        String hallo = "Hello, ";
        txtFullName.setText(hallo + name2);


        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
        userRef.child("Account").orderByChild("userId").equalTo(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentAccount = dataSnapshot.getValue(Account.class);
                for(final DataSnapshot accountChild : dataSnapshot.getChildren())
                {
                    String accountnumber = accountChild.child("accountNumber").getValue().toString();
                    String amount = accountChild.child("amount").getValue().toString();
                    txtAccountNum.setText("Account Number: " +accountnumber);
                    txtAmount.setText("Total Amount: " + amount +" Rupiah");
                    txtName.setText("Name: " +name2);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.deposit) {
            Intent DepositIntent = new Intent(Home.this,Deposit.class);
            startActivity(DepositIntent);
        }
        else if (id == R.id.transfer) {
            Intent TransferIntent = new Intent(Home.this,Transfer.class);
            startActivity(TransferIntent);
        }
        else if (id == R.id.mutation) {
            Intent MutationActivity = new Intent(Home.this,Mutation.class);
            startActivity(MutationActivity);
        }
        else if (id == R.id.logout) {
            Intent MainActivity = new Intent(Home.this,LogInActivity.class);
            MainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(MainActivity);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
