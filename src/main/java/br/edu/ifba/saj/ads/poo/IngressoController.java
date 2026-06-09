package br.edu.ifba.saj.ads.poo;

import java.time.LocalDate;

import br.edu.ifba.saj.ads.poo.data.Cinema;
import br.edu.ifba.saj.ads.poo.model.Categoria;
import br.edu.ifba.saj.ads.poo.model.Livro;
import br.edu.ifba.saj.ads.poo.model.Ingresso;
import br.edu.ifba.saj.ads.poo.model.Autor;
import br.edu.ifba.saj.ads.poo.model.TipoIngresso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

public class IngressoController {

    @FXML
    private ChoiceBox<Livro> slFilme;

    @FXML
    private ChoiceBox<Autor> slHorarios;

    @FXML
    private ChoiceBox<TipoIngresso> slTipoIngresso;

    private Livro livroSelecionado;
    private Autor autorSelecionada;
    // private TipoIngresso tipoIngressoSelecionado;

    @FXML
    private void initialize() {
        slFilme.getItems().addAll(Cinema.livros);
        slTipoIngresso.getItems().addAll(TipoIngresso.values());

        slFilme.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                livroSelecionado = newValue;
                slHorarios.getItems().addAll(livroSelecionado.getSessoes());
            }
        });

        slFilme.setConverter(new StringConverter<Livro>() {
            @Override
            public String toString(Livro livro) {
                return livro == null ? "" : livro.getTitulo();
            }

            @Override
            public Livro fromString(String string) {
                return null;
            }
        });

        slHorarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                autorSelecionada = newValue;
            }
        });

        slHorarios.setConverter(new StringConverter<Autor>() {
            @Override
            public String toString(Autor autor) {
                return autor == null ? ""
                        : String.format("%1$td/%1$tm/%1$tY %1$tH:%1$tM ", autor.getHorario());
            }

            @Override
            public Autor fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    void salvar(ActionEvent event) {
        Ingresso ingresso = autorSelecionada.venderIngresso(
                new Categoria("Leandro", "01234567891", LocalDate.of(1983, 6, 4)),
                slTipoIngresso.getSelectionModel().getSelectedItem());
        if (ingresso != null) {

            new Alert(AlertType.INFORMATION, String.format(
                    "Ingresso %s vendido para o filme %s na sessão de %3$td/%3$tm/%3$tY %3$tH:%3$tM. Ingressos disponíveis %4$d ",
                    ingresso.getTipoIngresso(),
                    ingresso.getSessao().getFilme().getTitulo(),
                    ingresso.getSessao().getHorario(),
                    ingresso.getSessao().quantidadeIngressosDisponiveis())).showAndWait();
        } else {
            new Alert(AlertType.ERROR, "Sessão lotada").showAndWait();
        }

    }

    public Livro getFilmeSelecionado() {
        return livroSelecionado;
    }

    public Autor getSessaoSelecionada() {
        return autorSelecionada;
    }

}
