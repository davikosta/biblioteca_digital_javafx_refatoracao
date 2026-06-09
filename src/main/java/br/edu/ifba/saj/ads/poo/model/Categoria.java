package br.edu.ifba.saj.ads.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Categoria {

    private String nome;
    private List<Livro> livros;

    public Categoria (String nome) {
        this.nome = nome;
        this.livros = new ArrayList<>();
    }

    public void addLivro(Livro livro) {
        if (!this.livros.contains(livro)) {
            livros.add(livro);
        }
    }

    public String getNome() {
        return this.nome;
    }

    public List<Livro> getLivros () {
        return this.livros;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        Categoria other = (Categoria) obj;
        if (nome == null) {
            if (other.nome != null) {return false;}
        } else if (!nome.equals(other.nome)) {
            return false;
        }
        return true;
    }
    

}
