package br.edu.ifba.saj.ads.poo;

import java.time.LocalDate;

import br.edu.ifba.saj.ads.poo.data.Cinema;
import br.edu.ifba.saj.ads.poo.model.Cliente;
import br.edu.ifba.saj.ads.poo.model.Filme;
import br.edu.ifba.saj.ads.poo.model.Ingresso;
import br.edu.ifba.saj.ads.poo.model.Sessao;
import br.edu.ifba.saj.ads.poo.model.TipoIngresso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

public class IngressoController {

    @FXML
    private ChoiceBox<Filme> slFilme;

    @FXML
    private ChoiceBox<Sessao> slHorarios;

    @FXML
    private ChoiceBox<TipoIngresso> slTipoIngresso;

    private Filme filmeSelecionado;
    private Sessao sessaoSelecionada;
    // private TipoIngresso tipoIngressoSelecionado;

    @FXML
    private void initialize() {
        slFilme.getItems().addAll(Cinema.filmes);
        slTipoIngresso.getItems().addAll(TipoIngresso.values());

        slFilme.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filmeSelecionado = newValue;
                slHorarios.getItems().addAll(filmeSelecionado.getSessoes());
            }
        });

        slFilme.setConverter(new StringConverter<Filme>() {
            @Override
            public String toString(Filme filme) {
                return filme == null ? "" : filme.getTitulo();
            }

            @Override
            public Filme fromString(String string) {
                return null;
            }
        });

        slHorarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                sessaoSelecionada = newValue;
            }
        });

        slHorarios.setConverter(new StringConverter<Sessao>() {
            @Override
            public String toString(Sessao sessao) {
                return sessao == null ? ""
                        : String.format("%1$td/%1$tm/%1$tY %1$tH:%1$tM ", sessao.getHorario());
            }

            @Override
            public Sessao fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    void salvar(ActionEvent event) {
        Ingresso ingresso = sessaoSelecionada.venderIngresso(
                new Cliente("Leandro", "01234567891", LocalDate.of(1983, 6, 4)),
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

    public Filme getFilmeSelecionado() {
        return filmeSelecionado;
    }

    public Sessao getSessaoSelecionada() {
        return sessaoSelecionada;
    }

}
