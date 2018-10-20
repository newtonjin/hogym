package com.hog.newto.pf2;

import java.util.ArrayList;

public class Treino {
    private String nomeTreino;
    private ArrayList<Exercicio> exercicio=new ArrayList<>();

    public Treino() {
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
}
