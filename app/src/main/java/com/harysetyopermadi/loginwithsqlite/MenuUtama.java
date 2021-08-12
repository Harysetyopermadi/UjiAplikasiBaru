package com.harysetyopermadi.loginwithsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harysetyopermadi.loginwithsqlite.logindanregis.Login;

public class MenuUtama extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseDatabase fd;
    FirebaseUser fu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        fAuth=FirebaseAuth.getInstance();
        fd=FirebaseDatabase.getInstance();
        fu=fAuth.getCurrentUser();
        final TextView vnama= findViewById(R.id.usrnmhome);
        final TextView vemail= findViewById(R.id.emailutama);
        final ImageButton vkeluar=findViewById(R.id.btnkeluar);

        DatabaseReference dr=fd.getReference("Users").child(fAuth.getCurrentUser().getUid()).child("namauser");


        vemail.setText(fu.getEmail());
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String val=snapshot.getValue(String.class);

                vnama.setText(val);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        vkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i =new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                // startActivity(new Intent(getApplicationContext(), Login.class));
                //Toast.makeText(MenuUtama.this, "Berhasil Logout",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Berhasil Logut",Toast.LENGTH_SHORT).show();
                finishActivity();
            }
        });

    }

    private void finishActivity() {
        if(getApplicationContext() != null) {
            getApplicationContext();
            finish();
        }
    }
}