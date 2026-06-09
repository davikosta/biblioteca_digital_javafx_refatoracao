package br.edu.ifba.saj.ads.poo;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.function.UnaryOperator;

import br.edu.ifba.saj.ads.poo.data.Cinema;
import br.edu.ifba.saj.ads.poo.model.Livro;
import br.edu.ifba.saj.ads.poo.model.Autor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class SessaoController {


    @FXML
    private DatePicker dtHorarioDia;
    @FXML
    private Spinner<Integer> dtHorarioHora;
    @FXML
    private Spinner<Integer> dtHorarioMinuto;

    @FXML
    private ChoiceBox<Livro> slFilme;

    @FXML
    private TextField txQuantidade;

    @FXML
    private TextField txValor;

    private Livro livroSelecionado;

    @FXML
    private void initialize() {
        slFilme.getItems().addAll(Cinema.livros);
        // quando um filme for selecionado na lista, vai guardar esse filme na variaval
        // "filmeSelecionado"
        slFilme.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                livroSelecionado = newValue;
            }
        });
        // Define qual a informação do filme vai ser exibida na lista
        slFilme.setConverter(new StringConverter<Livro>() {
            @Override
            public String toString(Livro livro) {
                // Define o texto que o usuário vai ver na tela
                return livro == null ? "" : livro.getTitulo();
            }

            @Override
            public Livro fromString(String string) {
                // Não é necessário implementar para ChoiceBox padrão (pode retornar null)
                return null;
            }
        });

        String regexFloat = "^[0-9]*\\.?[0-9]*$";

        txValor.setStyle("-fx-alignment: CENTER-RIGHT;");

        txValor.setTextFormatter(new TextFormatter<>(change -> {
            // Se for uma ação de deletar ou resetar texto completo, permite
            if (change.isDeleted()) {
                return change;
            }

            // Pega o texto que resultaria da alteração e remove tudo que não for número
            String textoApenasNumeros = change.getControlNewText().replaceAll("[^0-9]", "");

            if (textoApenasNumeros.isEmpty()) {
                change.setText("0.00");
                change.setCaretPosition(4);
                change.setAnchor(4);
                return change;
            }

            // Converte para Long para remover zeros à esquerda desnecessários
            long valorLong = Long.parseLong(textoApenasNumeros);

            // Transforma de volta em String formatando com duas casas decimais
            // Exemplo: 125 vira "1.25"
            String novoTextoFormatado = String.format("%.2f", valorLong / 100.0);


            // Define o texto final do componente
            int tamanhoTexto = novoTextoFormatado.length();
            change.setRange(0, change.getControlText().length());
            change.setText(novoTextoFormatado);
            change.setCaretPosition(tamanhoTexto);
            change.setAnchor(tamanhoTexto);

            return change;
        }));

        // Inicializa o campo com valor padrão zerado
        txValor.setText("0.00");

        // Garante que o cursor sempre fique no final do texto ao clicar no campo
        txValor.focusedProperty().addListener((obs, antigo, novoFoco) -> {
            if (novoFoco) {
                Platform.runLater(txValor::end);
            }
        });

        // Expressão regular que aceita apenas dígitos (0-9) e permite que o campo fique
        // vazio
        UnaryOperator<TextFormatter.Change> filtroInteiro = change -> {
            String novoTexto = change.getControlNewText();
            if (novoTexto.matches("\\d*")) {
                return change; // Permite a alteração
            }
            return null; // Rejeita a alteração se contiver letras ou caracteres inválidos
        };

        // Aplica o filtro formatador no TextField
        txQuantidade.setTextFormatter(new TextFormatter<>(filtroInteiro));
    }

    private LocalDateTime getHorarioSelecionado() {
        LocalDate date = dtHorarioDia.getValue();
        if (date == null) {
            return null;
        }

        int hour = dtHorarioHora.getValue();
        int minute = dtHorarioMinuto.getValue();

        return LocalDateTime.of(date, LocalTime.of(hour, minute));
    }

    @FXML
    void salvar(ActionEvent event) throws NumberFormatException, ParseException {
        getFilmeSelecionado().addSessao(new Autor(getHorarioSelecionado(), livroSelecionado, Integer.valueOf(txQuantidade.getText()), NumberFormat.getNumberInstance(Locale.of("pt", "BR")).parse(txValor.getText()).floatValue()));

        new Alert(AlertType.INFORMATION,
                String.format("Horario filme %1$td/%1$tm/%1$tY %1$tH:%1$tM ", getHorarioSelecionado())).showAndWait();
        //new Alert(AlertType.INFORMATION, String.format("Nome filme %s ", getFilmeSelecionado().getNome()))
        //        .showAndWait();
        //new Alert(AlertType.INFORMATION, String.format("Sessoes filme %s  %s", getFilmeSelecionado().getNome(), getFilmeSelecionado().getSessoes().toString()))
        //        .showAndWait();

    }

    public Livro getFilmeSelecionado() {
        return livroSelecionado;
    }

}
