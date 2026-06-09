package br.edu.ifba.saj.ads.poo;

import java.util.Objects;

import br.edu.ifba.saj.ads.poo.data.Biblioteca;
import br.edu.ifba.saj.ads.poo.model.Autor;
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

public class AutorController {

    @FXML
    private TextField txNome;

    @FXML
    private TableColumn<Autor, String> clmNome;

    @FXML
    private TableView<Autor> tbAutores;

    @FXML
    private void initialize() {
        clmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        loadAutoresList();

        Platform.runLater(() -> txNome.requestFocus());
    }

    public void loadAutoresList() {
        tbAutores.setItems(FXCollections.observableList(Biblioteca.autores));
    }

    @FXML
    void salvar(ActionEvent event) {
        String nome = txNome.getText();

        if (Objects.nonNull(nome) && !nome.trim().isEmpty()) {
            Autor novoAutor = new Autor(nome);
            if (!Biblioteca.autores.contains(novoAutor)) {
                Biblioteca.autores.add(novoAutor);
                new Alert(AlertType.INFORMATION, String.format("Autor '%s' cadastrado com sucesso!", novoAutor.getNome())).showAndWait();
                txNome.clear();
            } else {
                new Alert(AlertType.WARNING, "Este autor já está cadastrado.").showAndWait();
            }
        } else {
            new Alert(AlertType.ERROR, "O nome do autor é obrigatório.").showAndWait();
        }

        loadAutoresList();
    }
}