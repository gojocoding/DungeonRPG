package it.unicam.cs.mpgc.rpg126277.ui;



import it.unicam.cs.mpgc.rpg126277.core.GameEngine;
import it.unicam.cs.mpgc.rpg126277.model.CharacterClass;
import it.unicam.cs.mpgc.rpg126277.model.GameState;
import it.unicam.cs.mpgc.rpg126277.model.Player;
import it.unicam.cs.mpgc.rpg126277.world.DungeonGenerator;
import it.unicam.cs.mpgc.rpg126277.world.RoomResult;
import it.unicam.cs.mpgc.rpg126277.world.Room;
import it.unicam.cs.mpgc.rpg126277.persistence.JsonSaveRepository;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private String playerName;

    private Button warriorCard;
    private Button mageCard;
    private CharacterClass selectedClass;

    private Label playerInfo = new Label();
    private Label roomInfo = new Label();
    private Label resultInfo = new Label();

    private UiState uiState = UiState.MENU;
    private VBox characterCreationRoot;
    private TextField nameField;
    private Button startGameBtn;


    @Override
    public void start(Stage stage) {

        createMenu();
        createCharacterCreation();
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

        newGameBtn.setOnAction(e -> showCharacterCreation());
        loadBtnMenu.setOnAction(e -> loadGame());

        menuRoot = new VBox(10, newGameBtn, loadBtnMenu);

        menuRoot.setStyle(
                "-fx-background-image: url('/images/manuu.jfif');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center;" +

                "-fx-alignment: center;" +
                        "-fx-padding: 20;"
        );
    }
    private void createCharacterCreation() {
        nameField = new TextField();
        nameField.setMaxWidth(200);
        nameField.setPrefWidth(200);
        nameField.setPromptText("Enter your name");
        nameField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 15 ? change : null
        ));

        Label title = new Label("Create Character");
        title.getStyleClass().add("creation-title");

        warriorCard = new Button("WARRIOR\nHP: 120\nATK: 15");
        mageCard = new Button("MAGE\nHP: 80\nATK: 10");

        warriorCard.getStyleClass().add("class-card");
        mageCard.getStyleClass().add("class-card");

        selectedClass = CharacterClass.WARRIOR;

        warriorCard.setOnAction(e -> selectClass(CharacterClass.WARRIOR));
        mageCard.setOnAction(e -> selectClass(CharacterClass.MAGE));

        updateClassSelectionUI();

        startGameBtn = new Button("Start Adventure");
        startGameBtn.getStyleClass().add("rpg-button");
        startGameBtn.setOnAction(e -> startNewGameWithCharacter());

        HBox classBox = new HBox(20, warriorCard, mageCard);
        classBox.setAlignment(Pos.CENTER);

        characterCreationRoot = new VBox(
                15,
                title,
                nameField,
                classBox,
                startGameBtn
        );

        characterCreationRoot.setAlignment(Pos.CENTER);
        characterCreationRoot.setStyle(
                "-fx-background-color: #0b0b0b;"
        );
    }

    private void selectClass(CharacterClass classe) {
        selectedClass = classe;
        updateClassSelectionUI();
    }

    private void updateClassSelectionUI() {

        warriorCard.setStyle("");
        mageCard.setStyle("");

        if (selectedClass == CharacterClass.WARRIOR) {
            warriorCard.setStyle(
                    "-fx-background-color: #333;" +
                            "-fx-text-fill: white;" +
                            "-fx-border-color: gold;" +
                            "-fx-border-width: 2;"
            );
        } else {
            mageCard.setStyle(
                    "-fx-background-color: #333;" +
                            "-fx-text-fill: white;" +
                            "-fx-border-color: gold;" +
                            "-fx-border-width: 2;"
            );
        }
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
        hpBar.setStyle(
                "-fx-accent: linear-gradient(to right, #ff4d4d, #b30000);"
        );

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
    private void startNewGameWithCharacter() {

        String name = nameField.getText();
        playerName = name;

        if (name == null || name.isBlank()) {
            name = "Hero";
        }

        if (selectedClass == null) {
            selectedClass = CharacterClass.WARRIOR;
        }

        Player player = new Player(name, selectedClass);

        GameState state = new GameState(
                player,
                DungeonGenerator.generateDungeon(5)
        );

        engine = new GameEngine(
                state,
                new JsonSaveRepository()
        );

        showGame();
    }

    private void loadGame() {

        if (engine == null) {
            engine = new GameEngine(
                    TestGameFactory.createTestGame(),
                    new JsonSaveRepository()
            );
        }

        engine.loadGame(playerName);
        showGame();
    }
    private void showCharacterCreation() {
        nameField.clear();
        scene.setRoot(characterCreationRoot);
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