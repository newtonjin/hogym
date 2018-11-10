package com.hog.newto.pf2;

import android.content.Context;
import android.database.Cursor;


import java.util.ArrayList;
import java.util.List;



public class Treino {
    private String idTreino;
    private String nomeTreino;

 ;

    public Treino(){

    }


    public Treino(String idTreino,String nomeTreino) {
        this.nomeTreino = nomeTreino;
        this.idTreino=idTreino;



    }

    public String getIdTreino() {
        return idTreino;
    }

    public void setIdTreino(String idTreino) {
        this.idTreino = idTreino;
    }

    public String getNomeTreino() {
        return nomeTreino;
    }

    public void setNomeTreino(String nomeTreino) {
        this.nomeTreino = nomeTreino;
    }






}
