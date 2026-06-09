package br.edu.ifba.saj.ads.poo;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;

import br.edu.ifba.saj.ads.poo.data.Cinema;
import br.edu.ifba.saj.ads.poo.model.Filme;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;

public class FilmeController {

    @FXML
    private TextField txDuracao;

    @FXML
    private TextField txNome;

    @FXML
    private TableColumn<Filme, Float> clmDuracao;

    @FXML
    private TableColumn<Filme, String> clmNome;

    @FXML
    private TableView<Filme> tbFilmes;

    @FXML
    private void initialize() {

        clmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        clmDuracao.setCellValueFactory(new PropertyValueFactory<>("duracao"));
        
        var formatadorDecimal = NumberFormat.getNumberInstance(Locale.of("pt", "BR"));
        
        //formatadorDecimal.setMaximumFractionDigits(2);

        clmDuracao.setCellFactory(coluna -> new TableCell<>() {
            @Override
            protected void updateItem(Float valor, boolean vazio) {
                super.updateItem(valor, vazio);

                if (vazio || valor == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Alinha números à direita e formata com vírgula (ex: 1234.56 -> 1.234,56)
                    setStyle("-fx-alignment: CENTER_RIGHT;");
                    setText(formatadorDecimal.format(valor));
                }
            }
        });
        loadFilmesList();

        UnaryOperator<TextFormatter.Change> filtroInteiro = change -> {
            String novoTexto = change.getControlNewText();
            if (novoTexto.matches("\\d*,?\\d*")) {
                return change; // Permite a alteração
            }
            return null; // Rejeita a alteração se contiver letras ou caracteres inválidos
        };

        txDuracao.setTextFormatter(new TextFormatter<>(filtroInteiro));

        Platform.runLater(() -> txNome.requestFocus());
    }

    public void loadFilmesList() {
        tbFilmes.setItems(FXCollections.observableList(Cinema.filmes));
    }

    @FXML
    void salvar(ActionEvent event) throws ParseException {
        if ((Objects.nonNull(txDuracao.getText())
                && !txDuracao.getText().isEmpty()
                && !txDuracao.getText().isEmpty())
                &&
                (Objects.nonNull(txNome.getText())
                        && !txNome.getText().isEmpty()
                        && !txNome.getText().isEmpty())) {
            Filme novoFilme = new Filme(txNome.getText(),
                    NumberFormat.getNumberInstance(Locale.of("pt", "BR")).parse(txDuracao.getText()).floatValue());
            Cinema.filmes.add(novoFilme);
            new Alert(AlertType.INFORMATION, String.format("Novo filme %s cadastrado", novoFilme.getTitulo()))
                    .showAndWait();

        } else {
            new Alert(AlertType.ERROR, "Nome e duração são informações obrigatórias").showAndWait();
        }

        loadFilmesList();

    }

}
