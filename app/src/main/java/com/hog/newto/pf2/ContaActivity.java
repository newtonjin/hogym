package com.hog.newto.pf2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContaActivity extends AppCompatActivity {
    private EditText txtNome;
    private Toolbar tlbar;
    private CircleImageView imgConta;
    private Button btnConfirma;
    private FirebaseAuth fb;
    private Uri imgURI=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
        tlbar=(Toolbar)findViewById(R.id.tlBar);
        setSupportActionBar(tlbar);
        getSupportActionBar().setTitle("Minha Conta");
        txtNome=(EditText)findViewById(R.id.etxNome);
        imgConta=(CircleImageView)findViewById(R.id.imgConta);
        btnConfirma=(Button)findViewById(R.id.btnConfirma);
        fb=FirebaseAuth.getInstance();


        btnConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome=txtNome.getText().toString();
                if(!TextUtils.isEmpty(nome)){
                Intent intentTreino=new Intent(ContaActivity.this,TreinosActivity.class);
                startActivity(intentTreino);
                finish();

            }}
        });

        imgConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Testa permissão do android para acessar ao armazenamento e trocar a foto
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(ContaActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(ContaActivity.this,"Permissão não concedida",Toast.LENGTH_LONG).show();
                        //Mostra balão de permissão
                        ActivityCompat.requestPermissions(ContaActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);



                    }else{
                        //código do git pra selecionar a imagem e recortar
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(ContaActivity.this);
                    }
                }
            }
        });
    }
    //método após modificação de imagem, ele pega o resultado do corte.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imgURI= result.getUri();
                imgConta.setImageURI(imgURI);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
