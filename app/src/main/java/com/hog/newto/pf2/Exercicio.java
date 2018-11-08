package com.hog.newto.pf2;

public class Exercicio {

    private String nomeEx;
    private int Rep;
    private int tempo;
    private Integer idTreino;

    public Integer getIdTreino() {
        return idTreino;
    }

    public void setIdTreino(Integer idTreino) {
        this.idTreino = idTreino;
    }

    public Exercicio() {


    }

    public Exercicio(String _nome, int _rep, int _tempo){

        this.nomeEx=_nome;
        this.Rep=_rep;
        this.tempo=_tempo;

    }




    public String getNomeEx() {
        return nomeEx;
    }

    public void setNomeEx(String nomeEx) {
        this.nomeEx = nomeEx;
    }

    public int getRep() {
        return Rep;
    }

    public void setRep(int rep) {
        Rep = rep;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }


}
