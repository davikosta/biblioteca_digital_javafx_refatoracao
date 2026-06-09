package br.edu.ifba.saj.ads.poo.model;

import java.util.ArrayList;
import java.util.List;

public class Livro {
    private String titulo;
    private Autor autor;
    private List<Categoria> categorias;

    public Livro(String titulo, Autor autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.categorias = new ArrayList<>();

        if (this.autor != null) {this.autor.addLivro(this);}
    }

    public void addCategoria(Categoria categoria) {
        if (!categorias.contains(categoria)) {
            categorias.add(categoria);
            categoria.addLivro(this);
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public List<Categoria> getCategorias() {
        return List.copyOf(categorias);
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        Livro other = (Livro) obj;
        if (titulo == null) {
            if (other.titulo != null) {return false;}
            return false;
        } else if (!titulo.equals(other.titulo)) {return false;}
        return true;
    }

}
