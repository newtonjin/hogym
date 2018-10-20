package com.hog.newto.pf2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContaActivity extends AppCompatActivity {
    private EditText txtNome;
    private Toolbar tlbar;
    private CircleImageView imgConta;
    private Button btnConfirma;
    private FirebaseAuth fb;
    private Uri imgURI=null;
    private StorageReference sr;
    private ProgressBar pb;
    private FirebaseFirestore fbs;
    private String user_id;
    private LineChart grPeso;
    private LineData grData;
    private EditText txtPeso;
    private EditText txtAltura;
    private String alturaS;
    private String pesoS;
    private float peso;
    private double imc,alturaD,pesoD;
    public int iss;
    private TextView txtAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conta);
        txtAdd=(TextView)findViewById(R.id.txtAdd);
        tlbar=(Toolbar)findViewById(R.id.tlBar);
        txtAltura=(EditText)findViewById(R.id.etxAltura);
        setSupportActionBar(tlbar);

        getSupportActionBar().setTitle("Minha Conta");

        txtNome=(EditText)findViewById(R.id.etxNome);

        imgConta=(CircleImageView)findViewById(R.id.imgConta);

        btnConfirma=(Button)findViewById(R.id.btnConfirma);


        fb=FirebaseAuth.getInstance();
        sr= FirebaseStorage.getInstance().getReference();
        fbs=FirebaseFirestore.getInstance();
        user_id=fb.getCurrentUser().getUid();

        txtAltura.setText("0");
        txtPeso=(EditText) findViewById(R.id.etxPeso);
        txtPeso.setText("0");


        grPeso=(LineChart)findViewById(R.id.grPeso);


        //pega os dados inseridos no FireStore para serem mostrados no layout de atividade
        fbs.collection("Usuarios").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    //checa se no id que pegamos lá em cima possue dados inseridos
                    if (task.getResult().exists()){
                     String name=task.getResult().getString("nome");
                     String imagem=task.getResult().getString("imagem");

                     txtNome.setText(name);



                     imgURI = Uri.parse(imagem);


                     //Utiliza da library do glide pra setar a imagem que a activity mostra para a que pegamos no fbscollection
                        RequestOptions PlaceholderRequest=new RequestOptions();
                        PlaceholderRequest.placeholder(R.mipmap.icprofile);
                        Glide.with(ContaActivity.this).setDefaultRequestOptions(PlaceholderRequest).load(imagem).into(imgConta);
                    }
                }else{
                    String erro=task.getException().toString();
                    Toast.makeText(ContaActivity.this,"Erro "+erro, Toast.LENGTH_LONG).show();
                }
            }
        });

        //Adiciona os imc ao gráfico

        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alturaS=txtAltura.getText().toString();
                pesoS=txtPeso.getText().toString();
                alturaD=Double.parseDouble(alturaS);
                pesoD=Double.parseDouble(pesoS);

                try {
                    geraIMC(alturaD, pesoD);

                    Toast.makeText(ContaActivity.this,"IMC gerado com sucesso", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    String erro=e.getMessage().toString();
                    Toast.makeText(ContaActivity.this, "Não foi possível gerar IMC"+  erro, Toast.LENGTH_SHORT).show();
                }
            }
        });




        btnConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nome=txtNome.getText().toString();


                if(!TextUtils.isEmpty(nome)){
                 //pega o id do usuário
                 user_id=fb.getCurrentUser().getUid();



                //faz com q a referencia de Storage do fb crie uma child com nome de "img_pefil"
                    // e essa child tenha uma outra filha que armazene o id e .jpg para formato de imagem
                 StorageReference img=sr.child("img_perfil").child(user_id+".jpg");


                 //após isso faz a operação de inserção da image(URI) com um listener para testar a tarefa
                 img.putFile(imgURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                         if(task.isSuccessful()){


                             //para poder baixar a imagem

                             Uri download_uri=task.getResult().getUploadSessionUri();
                             Toast.makeText(ContaActivity.this,"Upload feito com sucesso",Toast.LENGTH_LONG).show();


                             //Criação de um objeto para mapear o usuário com a imagem e os dados

                             Map<String, String> userMap=new HashMap<>();
                             userMap.put("nome",nome);
                             userMap.put("imagem",download_uri.toString());

                             //Faz uma colection do FireStore usando o ID do usuário e o mapeamento userMap criado
                             fbs.collection("Usuarios").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful()){
                                        sendToMain();
                                     }else{
                                         String erro=task.getException().toString();
                                         Toast.makeText(ContaActivity.this,"Erro no armazenamento da imagem "+erro, Toast.LENGTH_LONG).show();
                                     }

                                 }
                             });

                         }else{
                             String erro=task.getException().toString();
                             Toast.makeText(ContaActivity.this,"Não foi possível fazer upload "+erro, Toast.LENGTH_LONG).show();

                         }
                     }
                 });

               sendToMain();
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

                        selecionaImagem();

                    }
                }else{
                 selecionaImagem();
                }
            }
        });

        LineDataSet lds=new LineDataSet(dataValues(),"Peso");
        ArrayList<ILineDataSet> dataSets= new ArrayList<>();
        dataSets.add(lds);
        LineData ld=new LineData(dataSets);
        grPeso.setData(ld);
    }
    //código do git pra selecionar a imagem e recortar
    private void selecionaImagem() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(ContaActivity.this);
    }
    public void sendToMain(){
        Intent intentTreino=new Intent(ContaActivity.this,TreinosActivity.class);
        startActivity(intentTreino);
        finish();

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
    private ArrayList<Entry> dataValues() {
        ArrayList<Entry> dataVals = new ArrayList<>();
iss=0;



              while(iss<=4){
            dataVals.add(new Entry(iss, (float)geraIMC(alturaD,pesoD)));


            iss++;
        }
        return dataVals;
    }

    //Metodo para gerar IMC
    public double geraIMC(double altura, double peso){

       imc=  peso/altura;
        imc= Math.pow(imc,2);

        return imc;

    }

}
