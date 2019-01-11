package com.example.fabiomoscariello.recyclerviewprova.Model;

import android.net.Uri;

public class Animale {
String tipo;
Uri img;


    public Animale() {

        tipo = "Prova";
        img=null;
    }



    public Animale(String tipo,Uri img) {

        this.tipo = tipo;
        this.img= img;
    }
    public String getTipo() {
        return tipo;
    }
    public Animale(String tipo){
        this.tipo=tipo;
        img=null;
    }
}
