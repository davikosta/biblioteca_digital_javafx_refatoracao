package br.edu.ifba.saj.ads.poo.model;

import java.util.ArrayList;
import java.util.List;

public class Filme {
    private String titulo;

    public Filme(String titulo, float duracao) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
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
