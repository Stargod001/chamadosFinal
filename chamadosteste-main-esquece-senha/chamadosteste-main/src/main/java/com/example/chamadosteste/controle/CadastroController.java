package com.example.chamadosteste.controle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.chamadosteste.SpringContext;
import com.example.chamadosteste.modelo.Usuario;
import com.example.chamadosteste.repositorio.UsuarioRepository;

@Component
public class CadastroController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private PasswordField confirmarSenhaField;

    @FXML
    private Pane dragArea;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void initialize() {
        enableWindowDrag();
    }

    private void enableWindowDrag() {
        dragArea.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        dragArea.setOnMouseDragged(event -> {
            Stage stage = (Stage) dragArea.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    private void cadastrarUsuario() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        String confirmarSenha = confirmarSenhaField.getText();
    
        // 🔥 NOVA VALIDAÇÃO
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos para continuar!");
            return;
        }
    
        if (!senha.equals(confirmarSenha)) {
            mostrarAlerta("Erro", "As senhas não coincidem.");
            return;
        }
    
        if (usuarioRepository.findByEmail(email) != null) {
            mostrarAlerta("Erro", "E-mail já cadastrado.");
            return;
        }
    
        Usuario novoUsuario = new Usuario(null, nome, email, senha, "USUARIO");
        usuarioRepository.save(novoUsuario);
    
        mostrarAlerta("Sucesso", "Usuário cadastrado com sucesso!");
        fecharJanela();
    }
    
    // Da pra apagar isso vou deixar pra vcs ver se querem apagar
    // private void abrirLogin() {
    //     try {
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
    //         loader.setControllerFactory(SpringContext.getContext()::getBean); // ESSA LINHA é o segredo
    //         Parent root = loader.load();

    //         Stage stage = new Stage();
    //         stage.setTitle("Login");
    //         stage.setScene(new Scene(root, 350, 475));
    //         stage.initStyle(StageStyle.TRANSPARENT);
    //         stage.show();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    private void fecharJanela() {
    Stage stage = (Stage) nomeField.getScene().getWindow();
    stage.close();
    voltarParaLogin(); // 🔥 ADICIONADO: Depois de fechar, volta para o login
}


    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
