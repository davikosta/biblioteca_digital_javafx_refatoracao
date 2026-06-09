package br.edu.ifba.saj.ads.poo;

import java.util.stream.Collectors;
import br.edu.ifba.saj.ads.poo.data.Biblioteca;
import br.edu.ifba.saj.ads.poo.model.Autor;
import br.edu.ifba.saj.ads.poo.model.Categoria;
import br.edu.ifba.saj.ads.poo.model.Livro;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

public class PesquisaController {

    @FXML
    private ChoiceBox<Autor> slAutor;

    @FXML
    private ChoiceBox<Categoria> slCategoria;

    @FXML
    private TableView<Livro> tbLivros;

    @FXML
    private TableColumn<Livro, String> clmTitulo;

    @FXML
    private TableColumn<Livro, String> clmAutor;

    @FXML
    private TableColumn<Livro, String> clmCategorias;

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

        slAutor.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                slCategoria.getSelectionModel().clearSelection();
                pesquisarPorAutor(novo);
            }
        });

        slCategoria.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                slAutor.getSelectionModel().clearSelection();
                pesquisarPorCategoria(novo);
            }
        });

        exibirTodos();
    }

    private void pesquisarPorAutor(Autor autor) {
        var filtrados = Biblioteca.livros.stream()
                .filter(l -> autor.equals(l.getAutor()))
                .collect(Collectors.toList());
        tbLivros.setItems(FXCollections.observableList(filtrados));
    }

    private void pesquisarPorCategoria(Categoria categoria) {
        var filtrados = Biblioteca.livros.stream()
                .filter(l -> l.getCategorias().contains(categoria))
                .collect(Collectors.toList());
        tbLivros.setItems(FXCollections.observableList(filtrados));
    }

    @FXML
    void limparFiltros(ActionEvent event) {
        slAutor.getSelectionModel().clearSelection();
        slCategoria.getSelectionModel().clearSelection();
        exibirTodos();
    }

    private void exibirTodos() {
        tbLivros.setItems(FXCollections.observableList(Biblioteca.livros));
    }
}