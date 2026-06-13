package it.unicam.cs.mpgc.rpg126277.ui;



import it.unicam.cs.mpgc.rpg126277.core.GameEngine;
import it.unicam.cs.mpgc.rpg126277.model.GameState;
import it.unicam.cs.mpgc.rpg126277.world.RoomResult;
import it.unicam.cs.mpgc.rpg126277.world.Room;
import it.unicam.cs.mpgc.rpg126277.persistence.JsonSaveRepository;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameView extends Application {

    private GameEngine engine;

    private Scene scene;

    private VBox menuRoot;
    private BorderPane gameRoot;
    private ProgressBar hpBar = new ProgressBar();

    private Button nextRoomBtn;
    private Button saveBtn;
    private Button loadBtn;
    private Button restartBtn;

    private Label playerInfo = new Label();
    private Label roomInfo = new Label();
    private Label resultInfo = new Label();

    private UiState uiState = UiState.MENU;

    @Override
    public void start(Stage stage) {

        createMenu();
        createGameUI();

        scene = new Scene(menuRoot, 400, 300);

        scene.getStylesheets().add(
                getClass().getResource("/styles.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle("DungeonRPG");
        stage.show();
    }

    // ---------------- MENU ----------------

    private void createMenu() {

        Button newGameBtn = new Button("New Game");
        Button loadBtnMenu = new Button("Load");

        newGameBtn.getStyleClass().add("menu-button");
        loadBtnMenu.getStyleClass().add("menu-button");

        newGameBtn.setOnAction(e -> startNewGame());
        loadBtnMenu.setOnAction(e -> loadGame());

        menuRoot = new VBox(10, newGameBtn, loadBtnMenu);

        menuRoot.setStyle(
                "-fx-background-image: url('/images/maenu.jfif');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center;" +

                "-fx-alignment: center;" +
                        "-fx-padding: 20;"
        );
    }

    // ---------------- GAME UI ----------------

    private void createGameUI() {
        nextRoomBtn = new Button("Next Room");
        saveBtn = new Button("Save");
        restartBtn = new Button("Back to Menu");

        playerInfo.getStyleClass().add("stats-label");
        roomInfo.getStyleClass().add("stats-label");
        resultInfo.getStyleClass().add("stats-label");

        nextRoomBtn.getStyleClass().add("rpg-button");
        saveBtn.getStyleClass().add("rpg-button");
        restartBtn.getStyleClass().add("rpg-button");

        nextRoomBtn.setOnAction(e -> playTurn());
        saveBtn.setOnAction(e -> engine.saveGame());
        restartBtn.setOnAction(e -> showMenu());

        hpBar.setPrefWidth(200);
        hpBar.setStyle("-fx-accent: red;");

        // ---------------- INFO AREA (CENTRO) ----------------
        VBox infoBox = new VBox(5,
                playerInfo,
                hpBar,
                roomInfo,
                resultInfo
        );

        // ---------------- BOTTOM BAR ----------------
        HBox bottomBar = new HBox(20);
        bottomBar.setAlignment(Pos.CENTER);

        bottomBar.getChildren().addAll(
                nextRoomBtn,
                saveBtn,
                restartBtn
        );

        bottomBar.setStyle(
                "-fx-padding: 15;" +
                        "-fx-background-color: #1a1a1a;"
        );

        // ---------------- ROOT LAYOUT (HUD STYLE) ----------------
        BorderPane root = new BorderPane();

        root.setCenter(infoBox);
        root.setBottom(bottomBar);

        root.setStyle(
                "-fx-padding: 15;" +
                        "-fx-background-color: #0b0b0b;"
        );

        gameRoot = root;
    }

    // ---------------- FLOW ----------------

    private void startNewGame() {
        GameState state = TestGameFactory.createTestGame();
        engine = new GameEngine(state, new JsonSaveRepository());

        showGame();
    }

    private void loadGame() {
        if (engine == null) {
            engine = new GameEngine(
                    TestGameFactory.createTestGame(),
                    new JsonSaveRepository()
            );
        }

        engine.loadGame("Test");
        showGame();
    }

    private void showGame() {
        scene.setRoot(gameRoot);
        uiState = UiState.PLAYING;
        updateUI();
    }

    private void showMenu() {
        scene.setRoot(menuRoot);
        uiState = UiState.MENU;
    }

    // ---------------- GAME LOGIC ----------------

    private void playTurn() {

        RoomResult result = engine.nextTurn();
        resultInfo.setText(result.getMessage());

        if (engine.isFinished()) {
            uiState = UiState.GAME_OVER;
        }

        updateUI();
    }

    private void updateUI() {

        if (engine == null) return;

        var state = engine.getGameState();
        var player = state.getPlayer();

        playerInfo.setText(
                "Player: " + player.getName() +
                        " HP: " + player.getHp() +
                        " LV: " + player.getLevel()
        );

        Room current = state.getCurrentRoom();
        roomInfo.setText(current != null ? current.getType().toString() : "-");

        if (engine.isGameOver()) {
            resultInfo.setText(engine.isVictory() ? "🏆 YOU WIN!" : "☠ GAME OVER");
            uiState = UiState.GAME_OVER;
        }
        double hpRatio = (double) player.getHp() / player.getMaxHp();
        hpBar.setProgress(hpRatio);
    }

    // ---------------- STATE ----------------

    private enum UiState {
        MENU,
        PLAYING,
        GAME_OVER
    }
}