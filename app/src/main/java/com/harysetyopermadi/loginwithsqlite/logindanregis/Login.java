package com.harysetyopermadi.loginwithsqlite.logindanregis;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.harysetyopermadi.loginwithsqlite.R;

public class Login extends AppCompatActivity {

    TextInputEditText logtxtemail,logtxtpassword;

    ProgressBar pbl;
    FirebaseAuth fAuth;
    FirebaseDatabase fd;
    Button blmpnyakun,masuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //inisialisasi
        blmpnyakun=findViewById(R.id.daftarakun);
        masuk=findViewById(R.id.btnlogin);
        logtxtemail=findViewById(R.id.emaillogin);
        logtxtpassword=findViewById(R.id.passwordlogin);
        fAuth=FirebaseAuth.getInstance();
        fd=FirebaseDatabase.getInstance();
        pbl=findViewById(R.id.pb2);



        if (fAuth.getCurrentUser()!=null) {
          //  startActivity(new Intent(getApplicationContext(),Utama.class));
            //finish();

            //getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,new HomeFragment()).commit();
            //finish();
            //startActivity(new Intent(Login.this,Utama.class));
            //Intent intent=new Intent(Login.this,HomeFragment.class);
            //startActivity(intent);
            //finish();

        }
        ///statusbar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.ijomudabackgroud));
        }

        blmpnyakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Registrasi.class);
                startActivity(i);
                finish();
            }
        });


        pbl.setVisibility(View.GONE);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temail= logtxtemail.getText().toString().trim();
                String tpassword=logtxtpassword.getText().toString().trim();


                if (TextUtils.isEmpty(temail)){
                    logtxtemail.setError("Email Wajib di isi");
                    return;
                }
                if (TextUtils.isEmpty(tpassword)){
                    logtxtpassword.setError("Password wajib di isi");
                    return;
                }
                pbl.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(temail,tpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT).show();



                       /*DatabaseReference dr=fd.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("namauser");
                            dr.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    String val=snapshot.getValue(String.class);
                                    Toast.makeText(Login.this,"Berhasil Login" +val,Toast.LENGTH_SHORT).show();
                                    pbl.setVisibility(View.GONE);
                                    Intent intent= new Intent(Login.this,Utama.class);
                                    intent.putExtra("namauser",val);
                                    intent.putExtra("emailuser",FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());

                                    startActivity(intent);
                                    finish();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
*/

                        }   else {
                            Toast.makeText(Login.this,"Gagal Login" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            pbl.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

    }
}