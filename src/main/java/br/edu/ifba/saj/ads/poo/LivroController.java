package br.edu.ifba.saj.ads.poo;

import java.util.Objects;
import java.util.stream.Collectors;

import br.edu.ifba.saj.ads.poo.data.Biblioteca;
import br.edu.ifba.saj.ads.poo.model.Autor;
import br.edu.ifba.saj.ads.poo.model.Categoria;
import br.edu.ifba.saj.ads.poo.model.Livro;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

public class LivroController {

    @FXML
    private ChoiceBox<Autor> slAutor;

    @FXML
    private ChoiceBox<Categoria> slCategoria;

    @FXML
    private ListView<Categoria> lvCategorias;

    @FXML
    private TextField txTitulo;

    @FXML
    private TableColumn<Livro, String> clmAutor;

    @FXML
    private TableColumn<Livro, String> clmTitulo;

    @FXML
    private TableColumn<Livro, String> clmCategorias;

    @FXML
    private TableView<Livro> tbLivros;

    @FXML
    private void initialize() {
        clmTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        clmAutor.setCellValueFactory(celula -> {
            if (celula.getValue().getAutor() != null) {
                return new SimpleStringProperty(celula.getValue().getAutor().getNome());
            }
            return new SimpleStringProperty("");
        });

        clmCategorias.setCellValueFactory(celula -> {
            String categoriasStr = celula.getValue().getCategorias().stream()
                    .map(Categoria::getNome)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(categoriasStr);
        });

        slAutor.getItems().addAll(Biblioteca.autores);
        slAutor.setConverter(new StringConverter<Autor>() {
            @Override
            public String toString(Autor autor) {
                return autor == null ? "" : autor.getNome();
            }
            @Override
            public Autor fromString(String string) { return null; }
        });

        slCategoria.getItems().addAll(Biblioteca.categorias);
        slCategoria.setConverter(new StringConverter<Categoria>() {
            @Override
            public String toString(Categoria categoria) {
                return categoria == null ? "" : categoria.getNome();
            }
            @Override
            public Categoria fromString(String string) { return null; }
        });

        loadLivrosList();

        Platform.runLater(() -> txTitulo.requestFocus());
    }

    public void loadLivrosList() {
        tbLivros.setItems(FXCollections.observableList(Biblioteca.livros));
    }

    @FXML
    void adicionarCategoria(ActionEvent event) {
        Categoria selecionada = slCategoria.getSelectionModel().getSelectedItem();
        if (selecionada != null && !lvCategorias.getItems().contains(selecionada)) {
            lvCategorias.getItems().add(selecionada);
        }
    }

    @FXML
    void salvar(ActionEvent event) {
        String titulo = txTitulo.getText();
        Autor autorSelecionado = slAutor.getSelectionModel().getSelectedItem();

        if (Objects.nonNull(titulo) && !titulo.trim().isEmpty() && Objects.nonNull(autorSelecionado)) {
            Livro novoLivro = new Livro(titulo, autorSelecionado);

            for (Categoria cat : lvCategorias.getItems()) {
                novoLivro.addCategoria(cat);
            }

            Biblioteca.livros.add(novoLivro);

            new Alert(AlertType.INFORMATION, String.format("Novo livro '%s' cadastrado com sucesso!", novoLivro.getTitulo()))
                    .showAndWait();

            txTitulo.clear();
            slAutor.getSelectionModel().clearSelection();
            slCategoria.getSelectionModel().clearSelection();
            lvCategorias.getItems().clear();
        } else {
            new Alert(AlertType.ERROR, "Título e Autor são informações obrigatórias").showAndWait();
        }

        loadLivrosList();
    }
}