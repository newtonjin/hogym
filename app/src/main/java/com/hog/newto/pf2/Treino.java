package com.hog.newto.pf2;

import android.content.Context;
import android.database.Cursor;


import java.util.ArrayList;
import java.util.List;

import static com.hog.newto.pf2.DbGateway.gw;

public class Treino {
    private String nomeTreino;

    private ArrayList<Exercicio> exercicio=new ArrayList<>();

    public Treino(Context ctx) {
        gw=DbGateway.getInstance(ctx);
    }

    public Treino(String _nomeTreino) {
        this.nomeTreino = _nomeTreino;

    }

    public String getNomeTreino() {
        return nomeTreino;
    }

    public void setNomeTreino(String nomeTreino) {
        this.nomeTreino = nomeTreino;
    }

    public ArrayList<Exercicio> getExercicio() {
        return exercicio;
    }

    public void setExercicio(ArrayList<Exercicio> exercicio) {
        this.exercicio = exercicio;
    }



    public static List<Treino> retornarTodos(){
        List<Treino> treinos = new ArrayList<>();
        Cursor cursor= gw.getDatabase().rawQuery("SELECT * FROM tb_treinos", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("idTreino"));
            String nomeTreino = cursor.getString(cursor.getColumnIndex("nomeTreino"));

            treinos.add(new Treino(nomeTreino));
        }
        cursor.close();
        return treinos;
    }public Treino retornarUltimo(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM tb_treinos ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("idTreino"));
            String nomeTreino = cursor.getString(cursor.getColumnIndex("nomeTreino"));

            cursor.close();
            return new Treino(nomeTreino);
        }

        return null;
    }
}
