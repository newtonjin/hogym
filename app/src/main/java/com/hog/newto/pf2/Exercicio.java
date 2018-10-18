package com.hog.newto.pf2;

public class Exercicio {

    String nomeEx;
    int Rep;
    int tempo;
    Byte[] foto;

    public Exercicio() {


    }

    public Exercicio(String _nome, int _rep, int _tempo, Byte[] _foto){
        this.foto=_foto;
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

    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }
}
