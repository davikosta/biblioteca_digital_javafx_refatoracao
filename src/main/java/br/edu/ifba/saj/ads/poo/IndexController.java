package br.edu.ifba.saj.ads.poo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class IndexController {

    @FXML
    private BorderPane pane;

    @FXML
    public void abrirCadastrarLivro(ActionEvent event) {
        try {
            pane.setCenter(FXMLLoader.load(getClass().getResource("Livro.fxml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirPesquisarLivros(ActionEvent event) {
        try {
            pane.setCenter(FXMLLoader.load(getClass().getResource("Pesquisa.fxml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirCadastrarAutor(ActionEvent event) {
        try {
            pane.setCenter(FXMLLoader.load(getClass().getResource("Autor.fxml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirCadastrarCategoria(ActionEvent event) {
        try {
            pane.setCenter(FXMLLoader.load(getClass().getResource("Categoria.fxml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}