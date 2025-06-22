package com.example.chamadosteste;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class AppApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void init() throws Exception {
        // Inicializa o contexto do Spring Boot
        springContext = SpringApplication.run(AppApplication.class);

        // Define o contexto do Spring para ser acessível globalmente
        SpringContext.setContext(springContext);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carrega o arquivo FXML da tela de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));

        // Define o controlador da tela de login para ser gerenciado pelo Spring
        loader.setControllerFactory(springContext::getBean);

        Parent root = loader.load();

        // Configura a cena e o palco (janela)
        Scene scene = new Scene(root, 350, 475);
        scene.setFill(Color.TRANSPARENT); // Torna o fundo transparente para bordas personalizadas

        primaryStage.initStyle(StageStyle.TRANSPARENT); // Remove as bordas padrão da janela
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/fxml/LogoIDEAU.png")));
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);

        // Permite arrastar a janela sem as bordas padrão
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.show(); // Exibe a janela
    }

    @Override
    public void stop() throws Exception {
        // Fecha o contexto do Spring quando a aplicação é encerrada
        springContext.close();
    }

    public static void main(String[] args) {
        // Inicia a aplicação JavaFX
        launch(AppApplication.class, args);
    }
}
