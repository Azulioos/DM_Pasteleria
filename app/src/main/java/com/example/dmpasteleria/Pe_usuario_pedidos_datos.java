package com.example.dmpasteleria;

public class Pe_usuario_pedidos_datos {

    private String tamano, pan, condimentos, colores, extra, Estado_del_pedido;
    private float Precio_total;

    private Pe_usuario_pedidos_datos(){

    }

    private Pe_usuario_pedidos_datos(String tamano,String pan,String condimentos,String colores,String extra, String Estado_del_pedido, float Precio_total){
        this.tamano = tamano;
        this.pan = pan;
        this.condimentos = condimentos;
        this.colores = colores;
        this.extra = extra;
        this.Estado_del_pedido = Estado_del_pedido;
        this.Precio_total = Precio_total;

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

    public String getEstado_del_pedido(){
        return Estado_del_pedido;
    }

    public float getPrecio_total(){
        return Precio_total;
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

    public void setEstado_del_pedido(String Estado_del_pedido){
        this.Estado_del_pedido = Estado_del_pedido;
    }

    public void setPrecio_total(float Precio_total){
        this.Precio_total = Precio_total;
    }

}
