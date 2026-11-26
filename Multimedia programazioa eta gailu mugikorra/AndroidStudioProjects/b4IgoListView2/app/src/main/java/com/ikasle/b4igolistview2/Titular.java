package com.ikasle.b4igolistview2;

// Proba-datuak gordetzeko klasea: izenburua eta azpititulua
public class Titular
{
    // Atributuak: elementuaren izenburua eta azpititulua
    private String titulo;
    private String subtitulo;

    // Eraikitzailea
    public Titular(String tit, String sub){
        titulo = tit;
        subtitulo = sub;
    }
    // Izenburua itzuli
    public String getTitulo(){
        return titulo;
    }

    // Azpititulua itzuli
    public String getSubtitulo(){
        return subtitulo;
    }
}