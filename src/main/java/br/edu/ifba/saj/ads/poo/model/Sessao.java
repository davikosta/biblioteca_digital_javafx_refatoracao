package br.edu.ifba.saj.ads.poo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sessao {

    private LocalDateTime horario;
    private Filme filme;
    private List<Ingresso> ingressos;
    
    public Sessao(LocalDateTime horario, Filme filme, int capacidade, float valorIngresso) {
        this.horario = horario;
        this.filme = filme;
        this.ingressos = new ArrayList<>();
        for (int i = 0; i < capacidade; i++) {
            ingressos.add(new Ingresso(this, valorIngresso));
        }
    }

    public Ingresso venderIngresso(Cliente cliente, TipoIngresso tipoIngresso){
        if(ingressos.size() > 0){
            Ingresso ingressoVendido = ingressos.getLast();
            ingressos.removeLast();
            ingressoVendido.setCliente(cliente);
            ingressoVendido.setTipoIngresso(tipoIngresso);
            cliente.addIngresso(ingressoVendido);
            return ingressoVendido;
        }
        return null;
    }

    public int quantidadeIngressosDisponiveis(){
        return ingressos.size();
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Filme getFilme() {
        return filme;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((horario == null) ? 0 : horario.hashCode());
        result = prime * result + ((filme == null) ? 0 : filme.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {    
        if(obj instanceof Sessao s){
            return s.getFilme().equals(filme) && s.getHorario().equals(horario);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Sessao [horario=" + horario + ", filme=" + filme.getTitulo() + ", quantidadeIngressosDisponiveis()="
                + quantidadeIngressosDisponiveis() + "]";
    }    

    
    
//    private int capacidade;
//    private int quantidade;
//
//    public void novaVenda(){
//        quantidade++;
//    }
//
//    public boolean isDisponivel(){
//        return capacidade > quantidade;
//    }

    
    
}
