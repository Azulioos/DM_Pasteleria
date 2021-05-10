package com.example.dmpasteleria;

public class Pe_usuario_pedidos_datos {

    private String tamano, pan, condimentos, colores, extra;

    private Pe_usuario_pedidos_datos(){

    }

    private Pe_usuario_pedidos_datos(String tamano,String pan,String condimentos,String colores,String extra){
        this.tamano = tamano;
        this.pan = pan;
        this.condimentos = condimentos;
        this.colores = colores;
        this.extra = extra;

    }

    public String getTamano(){
        return tamano;
    }

    public String getPan(){
        return pan;
    }

    public String getCondimentos(){
        return condimentos;
    }

    public String getColores(){
        return colores;
    }

    public String getExtra(){
        return extra;
    }


    //Set

    public void setTamano(String tamano){
        this.tamano = tamano;
    }

    public void setPan(String pan){
        this.pan = pan;
    }

    public void setCondimentos(String condimentos){
        this.condimentos = condimentos;
    }

    public void setColores(String colores){
        this.colores = colores;
    }

    public void setExtra(String extra){
        this.extra = extra;
    }
}
