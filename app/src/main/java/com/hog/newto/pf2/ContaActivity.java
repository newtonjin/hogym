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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private DatabaseReference db;
    private String user_id;
    private LineChart grPeso;
    private PieChart grPie;
    private EditText txtPeso    ;
    private EditText txtAltura;
    private String alturaS;
    private String pesoS;
    private EditText dia;
    private Float diaF;
    private double Massa;
    private String diaS;
    private TextView txtDica;
    private ImageView btnVoltar;







    private double imc,alturaD,pesoD;

    private TextView txtAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conta);
        txtAdd=findViewById(R.id.txtAdd);
        tlbar=(Toolbar)findViewById(R.id.tlBar);
        txtAltura=(EditText)findViewById(R.id.etxAltura);
        setSupportActionBar(tlbar);
        txtDica=findViewById(R.id.txtDica);
        dia=findViewById(R.id.etxData);
        getSupportActionBar().setTitle("Minha Conta");
        btnVoltar=findViewById(R.id.btnVoltar);
        txtNome=(EditText)findViewById(R.id.etxNome);

        imgConta=(CircleImageView)findViewById(R.id.imgConta);

        btnConfirma=(Button)findViewById(R.id.btnConfirma);


        fb=FirebaseAuth.getInstance();
        sr= FirebaseStorage.getInstance().getReference();
        fbs=FirebaseFirestore.getInstance();
        user_id=fb.getCurrentUser().getUid();
        db=FirebaseDatabase.getInstance().getReference("Usuarios").child(user_id);
        db.setValue(user_id);

        txtAltura.setText("");
        txtPeso=(EditText) findViewById(R.id.etxPeso);
        txtPeso.setText("");





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

        //Referência aos gráficos
        grPeso=findViewById(R.id.grPeso);
        grPie=findViewById(R.id.grPie);
        final ArrayList<Entry> dataVals = new ArrayList<>();
        dataVals.add(new Entry(0,0));
        LineDataSet lds=new LineDataSet(dataVals,"imc");
        LineData dataSets= new LineData(lds);
        grPeso.setData(dataSets);
        LimitLine obesidade= new LimitLine(39f,"Obesidade");
        obesidade.setLineWidth(4f);
        obesidade.enableDashedLine(10f,10f,0f);
        obesidade.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        obesidade.setTextSize(15f);

        LimitLine magreza= new LimitLine(18.5f,"Magreza");
        magreza.setLineWidth(4f);
        magreza.enableDashedLine(10f,10f,0f);
        magreza.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        magreza.setTextSize(15f);

        YAxis eixoY= grPeso.getAxisLeft();
        eixoY.removeAllLimitLines();
        eixoY.addLimitLine(obesidade);
        eixoY.addLimitLine(magreza);
        eixoY.setAxisMaximum(100f);
        eixoY.enableGridDashedLine(10f,10f,0);
        eixoY.setDrawLimitLinesBehindData(true);
        //estilização para line chart
        grPeso.setDoubleTapToZoomEnabled(false);
        XAxis eixoX=grPeso.getXAxis();
        eixoX.setAxisMaximum(31f);
        grPeso.notifyDataSetChanged();
        grPeso.fitScreen();
        grPeso.setHorizontalScrollBarEnabled(true);
        grPeso.setAutoScaleMinMaxEnabled(true);

        grPeso.setNoDataText("Sem IMC para gráfico");
        grPeso.setBackgroundColor(getResources().getColor(R.color.branco));



        grPeso.invalidate();

        //Pie chart estilização
        final ArrayList<PieEntry> dataX = new ArrayList<>();
        PieDataSet dtSet= new PieDataSet(dataX,"Massa por peso");
        PieData pdata= new PieData(dtSet);
        grPie.setData(pdata);
        dtSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        grPie.notifyDataSetChanged();
        grPie.invalidate();



        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alturaS=txtAltura.getText().toString();
                pesoS=txtPeso.getText().toString();
                diaS=dia.getText().toString();

                if(!TextUtils.isEmpty(alturaS)&& !TextUtils.isEmpty(pesoS) && !TextUtils.isEmpty(diaS)){
                try {
                    //Faz o calculo do imc apartir do peso e altura e insere na lista DataVals
                    //configuração do line cart

                    diaF=Float.parseFloat(diaS);
                    alturaD=Double.parseDouble(alturaS);
                    pesoD=Double.parseDouble(pesoS);
                    imc=  pesoD/Math.pow((alturaD/100),2);

                    dataVals.add(new Entry(diaF,(float)imc));
                    Toast.makeText(ContaActivity.this,"IMC gerado com sucesso", Toast.LENGTH_LONG).show();

                    txtAltura.setText("");
                    txtPeso.setText("");
                    dia.setText("");
                    grPeso.notifyDataSetChanged();
                    grPeso.setVisibleXRangeMaximum((float)imc);





                    grPeso.invalidate();

                    grPeso.setAutoScaleMinMaxEnabled(true);





                    //Pie chart
                    Massa = pesoD-imc;

                    dataX.add(new PieEntry((float)Massa,1));
                    dataX.add(new PieEntry((float)imc,2));
                    grPie.notifyDataSetChanged();
                    grPie.invalidate();

                    if(imc<=17){
                        txtDica.setText("\nProvavelmente você esteja muito abaixo do peso, tome cuidado! Se alimente com alimentos mais ricos em vitaminas e carboidratos em geral! imc de "+(int)imc+" é considerado muito baixo! procure um nutricionista.");
                    }else if(imc>=18 && imc<=18.49){
                        txtDica.setText("\nVocê pode estar abaixo do seu peso, adapte sua dieta para uma maior riqueza de proteínas e carboidratos, seu imc é de "+(int)imc+", procure um nutricionista.");
                    }else if(imc>=18.50 && imc<=24.99){
                        txtDica.setText("\nVocê tá no seu peso ideal! obviamente que isto é uma aproximação, caso queira aumentar Massa Corporea ou músculos calcule seus macro nutrientes e adapte sua dieta! seu imc é de "+(int)imc+" procure um nutricionista!");
                    }else if(imc>=25 && imc<=29.99){
                        txtDica.setText("\nProvavelmente você esteja acima do peso! caso seja massa muscular não tem problemas, mas se for visivel a gordura é melhor adaptar sua dieta, aumentar em proteinas e diminuir em gorduras e carbos! seu imc é de "+(int)imc+" procure um nutricista!");
                    }else if(imc>=30 && imc<=34.99){
                        txtDica.setText("\nOpa! provavelmente você esteja no início de uma obesidade, tome cuidado, diminua gorduras e carboidratos em excesso da sua dieta! seu imc é de "+(int)imc+" procure um nutricista o mais rápido possível!");
                    }else if(imc>=35 && imc<=39.99){
                        txtDica.setText("\nProvavelmente você esteja muito acima de seu peso!! tome bastante cuidado! mude drasticamente quantidade de carboidratos e gorduras e pratique atividades, seu imc é de "+(int)imc+" procure um nutricista o mais rápido possível!");
                    }else if(imc>=40){
                        txtDica.setText("\nO indice de massa corpórea do seu corpo está muito além do ideal, tome bastante cuidado, sua saúde está sendo prejudicada, pratique atividades e diminua bastante os carboidratos e gorduras ingeridas, seu imc é de "+imc+" procure um nutricista urgentemente!");
                    }else{
                        txtDica.setText("");
                    }

                }catch (Exception e){
                    String erro=e.getMessage().toString();
                    Toast.makeText(ContaActivity.this, "Não foi possível gerar IMC"+  erro, Toast.LENGTH_LONG).show();
                }}else{
                    Toast.makeText( ContaActivity.this, "Por favor insira os valores", Toast.LENGTH_LONG).show();
                }
            }
        });


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContaActivity.this,TreinosActivity.class));
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



}
