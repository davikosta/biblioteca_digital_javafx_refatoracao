package br.edu.ifba.saj.ads.poo;

import java.util.Objects;

import br.edu.ifba.saj.ads.poo.data.Biblioteca;
import br.edu.ifba.saj.ads.poo.model.Categoria;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoriaController {

    @FXML
    private TextField txNome;

    @FXML
    private TableColumn<Categoria, String> clmNome;

    @FXML
    private TableView<Categoria> tbCategorias;

    @FXML
    private void initialize() {
        clmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        loadCategoriasList();

        Platform.runLater(() -> txNome.requestFocus());
    }

    public void loadCategoriasList() {
        tbCategorias.setItems(FXCollections.observableList(Biblioteca.categorias));
    }

    @FXML
    void salvar(ActionEvent event) {
        String nome = txNome.getText();

        if (Objects.nonNull(nome) && !nome.trim().isEmpty()) {
            Categoria novaCategoria = new Categoria(nome);
            if (!Biblioteca.categorias.contains(novaCategoria)) {
                Biblioteca.categorias.add(novaCategoria);
                new Alert(AlertType.INFORMATION, String.format("Categoria '%s' cadastrada com sucesso!", novaCategoria.getNome())).showAndWait();
                txNome.clear();
            } else {
                new Alert(AlertType.WARNING, "Esta categoria já está cadastrada.").showAndWait();
            }
        } else {
            new Alert(AlertType.ERROR, "O nome da categoria é obrigatório.").showAndWait();
        }

        loadCategoriasList();
    }
}