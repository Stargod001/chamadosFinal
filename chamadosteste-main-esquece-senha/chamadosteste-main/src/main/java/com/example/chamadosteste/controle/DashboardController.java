package com.example.chamadosteste.controle;

import com.example.chamadosteste.SpringContext;
import com.example.chamadosteste.modelo.Chamado;
import com.example.chamadosteste.modelo.PoliticaSla;
import com.example.chamadosteste.modelo.Prioridade;
import com.example.chamadosteste.modelo.StatusChamado;
import com.example.chamadosteste.modelo.Usuario;
import com.example.chamadosteste.repositorio.ChamadoRepository;
import com.example.chamadosteste.repositorio.PoliticaSlaRepository;
import com.example.chamadosteste.repositorio.PrioridadeRepository;
import com.example.chamadosteste.repositorio.StatusChamadoRepository;
import com.example.chamadosteste.repositorio.UsuarioRepository;
import com.example.chamadosteste.servico.LogService;
import com.example.chamadosteste.servico.LoginService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DashboardController {

    @FXML private BorderPane dragArea;
    @FXML private GridPane telaHome;
    @FXML private Pane telaDashboard;
    @FXML private Pane telaChamados;
    @FXML private Pane telaMeusChamados;
    @FXML private TextField txtTituloNovoChamado;
    @FXML private TextField txtDescricaoNovoChamado;
    @FXML private DatePicker dataAbertura;
    @FXML private ComboBox<String> selectPrioridade;
    @FXML private Button btnInicio;
    @FXML private Button btnDashboard;
    @FXML private Button btnChamados;
    @FXML private Button btnAbrir;
    @FXML private Button btnCard1;
    @FXML private Button btnCard2;
    @FXML private Button btnCard3;
    @FXML private TableView<Chamado> tabelaChamados;
    @FXML private TableColumn<Chamado, String> colAssunto;
    @FXML private TableColumn<Chamado, String> colDescricao;
    @FXML private TableColumn<Chamado, String> colStatus;
    @FXML private TableColumn<Chamado, String> colDataAbertura;
    @FXML private TableColumn<Chamado, String> colTicket;
    @FXML private TableColumn<Chamado, String> colPrioridade;
    @FXML private TableColumn<Chamado, String> colResponsavel;
    @FXML private ComboBox<String> comboFiltroStatus;
    @FXML private Label lblTituloMeusChamados;
    @FXML private TableColumn<Chamado, Void> colAcao;

    @Autowired private ChamadoRepository chamadoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private LoginService loginService;
    @Autowired private LogService logService;
    @Autowired private StatusChamadoRepository statusRepository;
    @Autowired private PrioridadeRepository prioridadeRepository;
    @Autowired private PoliticaSlaRepository politicaSlaRepository;

    @FXML
    public void initialize() {
        enableWindowDrag();
        btnInicio.getStyleClass().add("active");
        telaDashboard.setVisible(false);
        telaChamados.setVisible(false);
        telaMeusChamados.setVisible(false);
        telaHome.setVisible(true);

        selectPrioridade.getItems().addAll("Baixo", "Médio", "Alto", "Urgente");
        dataAbertura.setValue(LocalDate.now());

        // Configurar filtro de status
        comboFiltroStatus.getItems().addAll("Todos", "ABERTO", "EM_ANDAMENTO", "CONCLUIDO", "CANCELADO");
        comboFiltroStatus.setValue("Todos");
        comboFiltroStatus.setOnAction(e -> atualizarMeusChamados());

        configurarTabelaChamados();
        atualizarMeusChamados();
    }

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

    @FXML private void mostrarInicio() {
        resetSidebarButtons();
        btnInicio.getStyleClass().add("active");
        esconderTodasAsTelas();
        telaHome.setVisible(true);
    }

    @FXML private void mostrarDashboard() {
        resetSidebarButtons();
        btnDashboard.getStyleClass().add("active");
        esconderTodasAsTelas();
        telaDashboard.setVisible(true);
    }

    @FXML private void mostrarChamados() {
        resetSidebarButtons();
        btnChamados.getStyleClass().add("active");
        esconderTodasAsTelas();
        telaMeusChamados.setVisible(true);
        atualizarMeusChamados();
    }

    @FXML private void abrirNovoChamado() {
        resetSidebarButtons();
        btnAbrir.getStyleClass().add("active");
        esconderTodasAsTelas();
        telaChamados.setVisible(true);
    }

    @FXML private void chamadosCard() {
        resetSidebarButtons();
        btnChamados.getStyleClass().add("active");
        esconderTodasAsTelas();
        telaMeusChamados.setVisible(true);
        atualizarMeusChamados();
    }

    private void resetSidebarButtons() {
        btnInicio.getStyleClass().remove("active");
        btnDashboard.getStyleClass().remove("active");
        btnChamados.getStyleClass().remove("active");
        btnAbrir.getStyleClass().remove("active");
    }

    private void esconderTodasAsTelas() {
        telaHome.setVisible(false);
        telaDashboard.setVisible(false);
        telaChamados.setVisible(false);
        telaMeusChamados.setVisible(false);
    }

    @FXML private void logout() {
        Stage stage = (Stage) telaHome.getScene().getWindow();
        stage.close();
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

    private void configurarTabelaChamados() {
        colTicket.setCellValueFactory(c ->
            new SimpleStringProperty("TICKET #" + c.getValue().getIdChamado()));

        colPrioridade.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getPrioridade() != null ? c.getValue().getPrioridade().getNome() : "Desconhecida"
            ));

        colAssunto.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getAssunto()));

        colDescricao.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getDescricao()));

        colStatus.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getStatus() != null ? c.getValue().getStatus().getNome() : "Desconhecido"
            ));

        colDataAbertura.setCellValueFactory(c -> {
            Timestamp data = c.getValue().getDataAbertura();
            if (data != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                return new SimpleStringProperty(sdf.format(data));
            } else {
                return new SimpleStringProperty("");
            }
        });

        colResponsavel.setCellValueFactory(c -> {
            Usuario responsavel = c.getValue().getResponsavel();
            return new SimpleStringProperty(responsavel != null ? responsavel.getNome() : "Não atribuído");
        });

        adicionarColunaDeAcoes();
    }

    private void atualizarMeusChamados() {
        Usuario usuarioLogado = loginService.getUsuarioLogado();
        if (usuarioLogado == null) {
            return;
        }

        String tipoUsuario = usuarioLogado.getTipo();
        String filtroStatus = comboFiltroStatus.getValue();
        List<Chamado> chamados;

        // Atualizar título baseado no tipo de usuário
        switch (tipoUsuario.toUpperCase()) {
            case "CLIENTE":
                lblTituloMeusChamados.setText("Meus Chamados");
                chamados = chamadoRepository.findByUsuario(usuarioLogado);
                break;
            case "AGENTE":
                lblTituloMeusChamados.setText("Atribuídos");
                chamados = chamadoRepository.findByResponsavel(usuarioLogado);
                break;
            case "ADMIN":
                lblTituloMeusChamados.setText("Chamados");
                chamados = chamadoRepository.findAll();
                break;
            default:
                chamados = chamadoRepository.findByUsuario(usuarioLogado);
                break;
        }

        // Aplicar filtro de status se não for "Todos"
        if (!"Todos".equals(filtroStatus)) {
            chamados = chamados.stream()
                .filter(c -> c.getStatus() != null && filtroStatus.equals(c.getStatus().getNome()))
                .collect(Collectors.toList());
        }

        tabelaChamados.setItems(FXCollections.observableArrayList(chamados));
    }

    @FXML 
    private void cadastroChamado(ActionEvent event) {
        try {
            String assunto = txtTituloNovoChamado.getText();
            String descricao = txtDescricaoNovoChamado.getText();
            String prioridadeSelecionada = selectPrioridade.getValue();
            LocalDate dataSelecionada = dataAbertura.getValue();

            if (assunto.isEmpty() || descricao.isEmpty() || prioridadeSelecionada == null || dataSelecionada == null) {
                exibirAlerta("Preencha todos os campos.");
                return;
            }

            Usuario usuario = loginService.getUsuarioLogado();
            if (usuario == null) {
                exibirAlerta("Usuário não logado. Faça login novamente.");
                return;
            }

            // Buscar entidades do banco de dados
            StatusChamado status = statusRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Status ABERTO não encontrado"));

            Prioridade prioridade = switch (prioridadeSelecionada.toUpperCase()) {
                case "BAIXO" -> prioridadeRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Prioridade BAIXA não encontrada"));
                case "MÉDIO", "MÉDIA" -> prioridadeRepository.findById(2)
                    .orElseThrow(() -> new RuntimeException("Prioridade MÉDIA não encontrada"));
                case "ALTO" -> prioridadeRepository.findById(3)
                    .orElseThrow(() -> new RuntimeException("Prioridade ALTA não encontrada"));
                case "URGENTE" -> prioridadeRepository.findById(4)
                    .orElseThrow(() -> new RuntimeException("Prioridade URGENTE não encontrada"));
                default -> throw new RuntimeException("Prioridade inválida");
            };

            PoliticaSla sla = politicaSlaRepository.findByPrioridade(prioridade)
            .orElseThrow(() -> new RuntimeException("Política SLA não encontrada para prioridade"));


            Chamado chamado = new Chamado();
            chamado.setAssunto(assunto);
            chamado.setDescricao(descricao);
            chamado.setDataAbertura(new Timestamp(System.currentTimeMillis()));
            chamado.setUsuario(usuario);
            chamado.setStatus(status);
            chamado.setPrioridade(prioridade);
            chamado.setSla(sla);

            chamadoRepository.save(chamado);
            logService.registrar(usuario.getId_usuario(), "CRIACAO", "Chamado criado: " + assunto);
            exibirAlerta("Chamado salvo com sucesso!");
            atualizarMeusChamados();
            
            // Limpar campos após cadastro
            txtTituloNovoChamado.clear();
            txtDescricaoNovoChamado.clear();
            selectPrioridade.getSelectionModel().clearSelection();
            dataAbertura.setValue(LocalDate.now());
            
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta("Erro ao salvar chamado: " + e.getMessage());
        }
    }

    private void adicionarColunaDeAcoes() {
        colAcao.setCellFactory(param -> new TableCell<>() {
            private final HBox hbox = new HBox(5);
            private final Button btnExcluir = new Button("❌");
            private final Button btnAtribuir = new Button("👤");

            {
                btnExcluir.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-font-size: 16px;");
                btnAtribuir.setStyle("-fx-background-color: transparent; -fx-text-fill: blue; -fx-font-size: 16px;");
                
                btnExcluir.setOnAction(event -> {
                    Chamado chamado = getTableView().getItems().get(getIndex());
                    Usuario usuario = loginService.getUsuarioLogado();

                    if (usuario == null || !"ADMIN".equalsIgnoreCase(usuario.getTipo())) {
                        exibirAlerta("Apenas usuários ADMIN podem excluir chamados.");
                        return;
                    }

                    chamadoRepository.delete(chamado);
                    logService.registrar(usuario.getId_usuario(), "EXCLUSAO", "Chamado excluído: " + chamado.getIdChamado());
                    atualizarMeusChamados();
                    exibirAlerta("Chamado excluído com sucesso!");
                });

                btnAtribuir.setOnAction(event -> {
                    Chamado chamado = getTableView().getItems().get(getIndex());
                    Usuario usuario = loginService.getUsuarioLogado();

                    if (usuario == null || !"ADMIN".equalsIgnoreCase(usuario.getTipo())) {
                        exibirAlerta("Apenas usuários ADMIN podem atribuir chamados.");
                        return;
                    }

                    atribuirChamado(chamado);
                });

                hbox.getChildren().addAll(btnExcluir, btnAtribuir);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Usuario usuario = loginService.getUsuarioLogado();
                    if (usuario != null && "ADMIN".equalsIgnoreCase(usuario.getTipo())) {
                        setGraphic(hbox);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });
    }

    private void atribuirChamado(Chamado chamado) {
        try {
            List<Usuario> agentes = usuarioRepository.findByTipo("AGENTE");
            
            if (agentes.isEmpty()) {
                exibirAlerta("Nenhum agente disponível para atribuição.");
                return;
            }

            List<String> nomesAgentes = agentes.stream()
                .map(Usuario::getNome)
                .collect(Collectors.toList());

            ChoiceDialog<String> dialog = new ChoiceDialog<>(nomesAgentes.get(0), nomesAgentes);
            dialog.setTitle("Atribuir Chamado");
            dialog.setHeaderText("Selecione um agente para atribuir o chamado #" + chamado.getIdChamado());
            dialog.setContentText("Agente:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String nomeAgenteSelecionado = result.get();
                Usuario agenteSelecionado = agentes.stream()
                    .filter(a -> a.getNome().equals(nomeAgenteSelecionado))
                    .findFirst()
                    .orElse(null);

                if (agenteSelecionado != null) {
                    chamado.setResponsavel(agenteSelecionado);
                    if (chamado.getStatus() != null && "ABERTO".equals(chamado.getStatus().getNome())) {
                        StatusChamado status = statusRepository.findById(2)
                            .orElseThrow(() -> new RuntimeException("Status EM_ANDAMENTO não encontrado"));
                        chamado.setStatus(status);
                    }
                    chamadoRepository.save(chamado);
                    Usuario usuarioLogado = loginService.getUsuarioLogado();
                    logService.registrar(usuarioLogado.getId_usuario(), "ATRIBUICAO",
                            "Chamado #" + chamado.getIdChamado() + " atribuído para " + agenteSelecionado.getNome());
                    atualizarMeusChamados();
                    exibirAlerta("Chamado atribuído com sucesso para " + agenteSelecionado.getNome() + "!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta("Erro ao atribuir chamado: " + e.getMessage());
        }
    }

    private void exibirAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}