package com.hog.newto.pf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AddExerciciosActivity extends AppCompatActivity {
    private ImageView addExercicio;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercicios);


        addExercicio=findViewById(R.id.addExercicio);



        addExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaExercicio();
            }
        });
    }public void salvaExercicio(){
        Intent intent= new Intent(AddExerciciosActivity.this,AddTreinoActivity.class);
        startActivity(intent);
        finish();

    }
}
