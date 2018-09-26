package com.hog.newto.pf2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail;
    private EditText loginSenha;
    private Button btnLogar;
    private Button btnCadastro;
    private ProgressBar barrarLogin;
    private FirebaseAuth fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEmail=(EditText)findViewById(R.id.reg_email);
        loginSenha=(EditText)findViewById(R.id.reg_senha);
        btnLogar=(Button)findViewById(R.id.btnLogar);
        btnCadastro=(Button)findViewById(R.id.btnCadastro);
        barrarLogin=(ProgressBar)findViewById(R.id.brLogin);
        fb=FirebaseAuth.getInstance();


        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String loginEmailString= loginEmail.getText().toString();
            String loginSenhaString=loginSenha.getText().toString();
            //Testa se os campos estão com coisas escritas

            if(!TextUtils.isEmpty(loginEmailString) && !TextUtils.isEmpty(loginSenhaString)){
                //caso estejam manda para o firebase o login e senha como string

                fb.signInWithEmailAndPassword(loginEmailString,loginSenhaString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user= fb.getCurrentUser();
                        sendToMain();
                        barrarLogin.setVisibility(View.VISIBLE);
                    }else{
                        String e=task.getException().getMessage().toString();
                        Toast.makeText(LoginActivity.this,"Erro " + e,Toast.LENGTH_LONG).show();
                    }
                    }
                });barrarLogin.setVisibility(View.INVISIBLE);
                }
            }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //inicializa usuário do FireB
        FirebaseUser currentUser= fb.getCurrentUser();

        if(currentUser!=null){
            //caso esteja logado ele vai nos levar ao intent de treinos/TELA!!!
            sendToMain();


        }
    }

    private void sendToMain() {
        Intent intentTreinos = new Intent(LoginActivity.this,TreinosActivity.class);
        startActivity(intentTreinos);
        finish();
    }
}
