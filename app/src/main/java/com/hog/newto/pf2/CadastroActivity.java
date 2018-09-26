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

public class CadastroActivity extends AppCompatActivity {
    private EditText regEmail;
    private EditText regSenha;
    private Button btnCriaConta;
    private Button btnJaPossui;
    private ProgressBar brCadastro;
    private EditText regConfirma;
    private FirebaseAuth fb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        regEmail=(EditText)findViewById(R.id.reg_email);
        regSenha=(EditText)findViewById(R.id.reg_senha);
        regConfirma=(EditText)findViewById(R.id.reg_confirma);
        btnCriaConta=(Button)findViewById(R.id.btnCriarConta);
        btnJaPossui=(Button)findViewById(R.id.btnJaPossui);
        brCadastro=(ProgressBar)findViewById(R.id.brCadastro);

        fb=FirebaseAuth.getInstance();
        brCadastro.setVisibility(View.INVISIBLE);
        btnJaPossui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToLogin();
            }
        });
        btnCriaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passa os campos para Strings
                String email=regEmail.getText().toString();
                String senha=regSenha.getText().toString();
                String confSenha=regConfirma.getText().toString();


                //testa se nenhum campo tá vazio
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha) && !TextUtils.isEmpty(confSenha)){
                    //testa se as senhas batem
                    if(senha.equals(confSenha)){
                        //Faz aparecer a progress Bar
                        brCadastro.setVisibility(View.VISIBLE);
                    //Agora adiciona o listener para operação com o firebase
                        fb.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                            //se a tarefa for concluida com sucesso acessa o método sendToMain q manda para os treinos
                                if(task.isSuccessful()){

                                    Toast.makeText(CadastroActivity.this,"Conta criada com sucesso",Toast.LENGTH_LONG).show();
                                    sendToLogin();
                                    brCadastro.setVisibility(View.INVISIBLE);
                            }else{
                                String e = task.getException().getMessage();
                                Toast.makeText(CadastroActivity.this, "Não foi possível se cadastrar"+e, Toast.LENGTH_LONG).show();
                            }
                            }
                        });

                    }else{
                        Toast.makeText(CadastroActivity.this, "Senhas não correspondem", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser=fb.getCurrentUser();
        if(currentUser!=null){
            sendToMain();
        }
    }
    private void sendToMain() {
        Intent intentTreinos = new Intent(CadastroActivity.this,TreinosActivity.class);
        startActivity(intentTreinos);
        finish();
    }
    private void sendToLogin(){
        Intent intentLogin = new Intent(CadastroActivity.this,LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }
}
