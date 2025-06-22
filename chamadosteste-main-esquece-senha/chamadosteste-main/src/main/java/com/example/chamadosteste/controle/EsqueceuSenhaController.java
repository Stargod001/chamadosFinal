package com.example.chamadosteste.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.chamadosteste.SpringContext;
import com.example.chamadosteste.servico.UsuarioService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Component
public class EsqueceuSenhaController {

    // Isso faz mexer a tela sem a janela 
    @FXML
    private Pane dragArea;

    private double xOffset = 0;
    private double yOffset = 0;

    private void enableWindowDrag() {
        dragArea.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        dragArea.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) dragArea.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
    // aki acaba a funcao q permite mexer a tela sem janela

    @FXML
    TextField emailField, nomeField, senhaField;

    @FXML
    Button btnSenha;

    @Autowired
    private UsuarioService usuarioService;

    @FXML
    private void revelarSenha() {
        String email = emailField.getText();
        String nome = nomeField.getText();

        if (email.isEmpty() || nome.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Campos obrigatórios");
            alert.setContentText("Preencha seu nome e e-mail para recuperar a senha.");
            alert.showAndWait();
            return;
        } 
        
        String senha = usuarioService.recuperarSenha(nome, email);

        if (senha != null) {
            senhaField.setText(senha);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Usuário não encontrado");
            alert.setContentText("Verifique se os dados inseridos estão corretos.");
            alert.showAndWait();
            return;
        }   
    }

    @FXML
    private void voltarParaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            loader.setControllerFactory(SpringContext.getContext()::getBean);
            Parent root = loader.load();
            Stage loginStage = new Stage();
            Scene sceneLogin = new Scene(root, 350, 475);

            sceneLogin.setFill(Color.TRANSPARENT);
            loginStage.initStyle(StageStyle.TRANSPARENT);

            loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/fxml/LogoIDEAU.png")));
            loginStage.setTitle("Login");
            loginStage.setScene(sceneLogin);
            loginStage.show();

            fecharJanela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        enableWindowDrag();
        senhaField.setEditable(false);
        senhaField.setDisable(true);

         // Tirar foco dos TextField:
        Platform.runLater(() -> {
            dragArea.requestFocus(); // passa o foco para o Pane que não tem interação
        });
    }
}
