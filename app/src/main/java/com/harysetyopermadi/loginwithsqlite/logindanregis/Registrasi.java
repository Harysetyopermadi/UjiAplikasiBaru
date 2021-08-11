package com.harysetyopermadi.loginwithsqlite.logindanregis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.harysetyopermadi.loginwithsqlite.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Registrasi extends AppCompatActivity {
    TextInputEditText regtxtEmail, regtxtusername, regtxtPassword2;
    Button regbtnRegister;
    Button sdhpnyakun;
    ProgressBar pb;

    FirebaseAuth mAuth;
    FirebaseDatabase fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        //inisialisasi
        regtxtEmail=findViewById(R.id.emailregis);
        regtxtusername=findViewById(R.id.namapenggunaregis);
        regtxtPassword2=findViewById(R.id.passwordregis);
        sdhpnyakun=findViewById(R.id.masuklogin);
        regbtnRegister=findViewById(R.id.btnregis);
        pb=findViewById(R.id.pb1);


        mAuth = FirebaseAuth.getInstance();
        fd=FirebaseDatabase.getInstance();






        ///statusbar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.ijomudabackgroud));
        }
        //fungsi pindah ke login
        sdhpnyakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
        regbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String vemail= regtxtEmail.getText().toString().trim();
                String vpassword=regtxtPassword2.getText().toString().trim();
                final String vnamapengguna=regtxtusername.getText().toString().trim();


                if (TextUtils.isEmpty(vemail)){
                    regtxtEmail.setError("Email Wajib di isi");
                    return;
                }
                if (TextUtils.isEmpty(vpassword)){
                    regtxtPassword2.setError("Password wajib di isi");
                    return;
                }

                if (TextUtils.isEmpty(vnamapengguna)){
                    regtxtusername.setError("Nama Pengguna wajib di isi");
                    return;
                }
                pb.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(vemail,vpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            fd.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("namauser").setValue(vnamapengguna).addOnCompleteListener(new OnCompleteListener<Void>() {



                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Registrasi.this, "User Berhasil Ditambah",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                            Toast.makeText(Registrasi.this, "Registrasi Berhasil",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                            finish();
                        }else {
                            Toast.makeText(Registrasi.this,"Gagal Regis" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });






    }

}



