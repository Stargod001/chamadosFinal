package com.example.chamadosteste.controle;

import com.example.chamadosteste.SpringContext;
import com.example.chamadosteste.modelo.Usuario;
import com.example.chamadosteste.servico.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginController {

    @FXML private Pane dragArea;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;

    @Autowired
    private LoginService loginService;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {
        enableWindowDrag();
    }

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

    @FXML
    private void login(ActionEvent event) {
        String email = emailField.getText();
        String senha = senhaField.getText();
        Usuario usuario = loginService.autenticar(email, senha);

        if (usuario != null) {
            try {
                Stage stage = (Stage) dragArea.getScene().getWindow();
                stage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
                loader.setControllerFactory(SpringContext.getContext()::getBean);
                Parent root = loader.load();

                Stage dashboardStage = new Stage();
                Scene sceneDashboard = new Scene(root, 1300, 760);
                sceneDashboard.setFill(Color.TRANSPARENT);
                dashboardStage.initStyle(StageStyle.TRANSPARENT);
                dashboardStage.getIcons().add(new Image(getClass().getResourceAsStream("/fxml/LogoIDEAU.png")));
                dashboardStage.setTitle("Dashboard");
                dashboardStage.setScene(sceneDashboard);
                dashboardStage.show();

            } catch (Exception e) {
                e.printStackTrace();
                exibirAlerta("Erro ao carregar o Dashboard: " + e.getMessage());
            }
        } else {
            exibirAlerta("Credenciais inválidas. Tente novamente.");
        }
    }

    @FXML
    private void abrirCadastro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cadastro.fxml"));
            loader.setControllerFactory(SpringContext.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Usuário");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/fxml/LogoIDEAU.png")));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta("Erro ao abrir a tela de cadastro: " + e.getMessage());
        }
    }

    @FXML
    private void telaEsqueciSenha(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/esqueceuSenha.fxml"));
            loader.setControllerFactory(SpringContext.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Recuperar Senha");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/fxml/LogoIDEAU.png")));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta("Erro ao abrir a tela de recuperação de senha: " + e.getMessage());
        }
    }

    @FXML
    private void fecharAplicacao(ActionEvent event) {
        Stage stage = (Stage) dragArea.getScene().getWindow();
        stage.close();
    }

    private void exibirAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
