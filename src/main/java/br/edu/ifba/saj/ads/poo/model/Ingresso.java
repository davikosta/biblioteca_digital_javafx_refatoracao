package br.edu.ifba.saj.ads.poo.model;

public class Ingresso {
    private Autor autor;
    private float valor;
    private Categoria categoria;
    private TipoIngresso tipoIngresso;
    public Ingresso(Autor autor) {
        this.autor = autor;
    }    

    public Ingresso(Autor autor, float valorIngresso) {
        this.autor = autor;
        this.valor = valorIngresso;
    }

    public void setTipoIngresso(TipoIngresso tipoIngresso) {
        this.tipoIngresso = tipoIngresso;
    }

    public void setCliente(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Ingresso [sessao=" + autor + ", valor=" + valor + ", cliente=" + categoria + ", tipoIngresso="
                + tipoIngresso + "]";
    }

    public Categoria getCliente() {
        return categoria;
    }

    public Autor getSessao() {
        return autor;
    }

    public TipoIngresso getTipoIngresso() {
        return tipoIngresso;
    }

    public float getValor() {
        return valor;
    }

    

}
