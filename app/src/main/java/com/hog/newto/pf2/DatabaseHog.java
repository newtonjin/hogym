package com.hog.newto.pf2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHog extends SQLiteOpenHelper {
    private final static String TABELA_TREINOS="tb_treinos";
    private final static String TABELA_EXERCICIOS="tb_exercicio";
    private final static String ID_TREINO="idTreino";
    private final static String COLUNA_NOME_TREINO="nomeTreino";
    private final static String COLUNA_NOME_EXERCICIO="nomeExe";
    private final static String COLUNA_REPETICOES="repeticoes";
    private final static String COLUNA_TEMPO="tempo";
    private final static String COLUNA_FOTO="foto";




    public DatabaseHog(@Nullable Context context) {
        super(context, "db_hog", null
                , 3);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE IF NOT EXISTS "+TABELA_TREINOS+" ("+ID_TREINO+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+COLUNA_NOME_TREINO+" VARCHAR)";
        String query2=" CREATE TABLE IF NOT EXISTS "+TABELA_EXERCICIOS+"("+COLUNA_NOME_EXERCICIO+" VARCHAR PRIMARY KEY NOT NULL,"+COLUNA_REPETICOES+"  INTEGER,"+COLUNA_TEMPO+" INTEGER, "+COLUNA_FOTO+" blob) ";
        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //crud pra treino;
    public void addTreino(Treino treino){
        //pega o bd que criamos ali com as tabelas para inserção
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues valores= new ContentValues();


        //insere o valor de nome treino dentro de "valores"
        valores.put(COLUNA_NOME_TREINO,treino.getNomeTreino());
        //insere valores dentro do bd
        db.insert(TABELA_TREINOS,null,valores);
        db.close();
    }public void editaTreino(Treino treino){

    }public void mostraTreino(Treino treino){

    }public void apagaTreino(Treino treino){

    }//crud para exercicio;
    public void addExercicio(Exercicio exercicio){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        //isso aqui serve pra colocar as coisas no banco de dados
        //tipo ali vai subir o nome do exercício, que tá na classe do exercicio
        // na tabela onde tem o nome exercicio xD
        values.put(COLUNA_NOME_EXERCICIO, exercicio.getNomeEx());
        values.put(COLUNA_REPETICOES, exercicio.getRep());
        values.put(COLUNA_TEMPO, exercicio.getTempo());
       //vo comentar isso pra resolver esse erro dps values.put(COLUNA_FOTO,exercicio.getFoto());

        //ali em cima a gente guardou todos os valores dentro da variável "values"
        //agora a gente insere dentro da tabela
        //inserimos dentro da "TABELA_EXERCICIO" os valores da variavel "Values"
        db.insert(TABELA_EXERCICIOS,null,values);
        db.close();

        //fueda

    }


}
