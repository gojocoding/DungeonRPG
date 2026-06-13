package it.unicam.cs.mpgc.rpg126277.ui;


import it.unicam.cs.mpgc.rpg126277.core.GameEngine;
import it.unicam.cs.mpgc.rpg126277.model.CharacterClass;
import it.unicam.cs.mpgc.rpg126277.model.GameState;
import it.unicam.cs.mpgc.rpg126277.model.Player;
import it.unicam.cs.mpgc.rpg126277.persistence.JsonSaveRepository;
import it.unicam.cs.mpgc.rpg126277.world.DungeonGenerator;
import it.unicam.cs.mpgc.rpg126277.world.Room;
import it.unicam.cs.mpgc.rpg126277.world.RoomResult;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameView extends Application {
    private GameEngine engine;
    private Scene scene;
    private CharacterClass selectedClass;
    private String currentSaveName;
    private boolean gameStarted = false;
    private UiState uiState = UiState.MENU;

    private VBox menuRoot;
    private BorderPane gameRoot;
    private ProgressBar hpBar = new ProgressBar();
    private VBox characterCreationRoot;
    private TextField nameField;

    private Button startGameBtn;
    private Button nextRoomBtn;
    private Button saveBtn;
    private Button loadBtn;
    private Button restartBtn;
    private Button attackBtn;
    private Button warriorCard;
    private Button mageCard;

    private Label playerInfo = new Label();
    private Label roomInfo = new Label();
    private Label resultInfo = new Label();
    private Label nameError = new Label();

    @Override
    public void start(Stage stage) {
        createMenu();
        createCharacterCreation();
        createGameUI();

        scene = new Scene(menuRoot, 500, 400);

        scene.getStylesheets().add(
                getClass().getResource("/styles.css").toExternalForm()
        );
        stage.setScene(scene);
        stage.setTitle("DungeonRPG");
        stage.show();
    }

    //Menù

    private void createMenu() {
        Button newGameBtn = new Button("Nuova Partita");
        Button loadBtnMenu = new Button("Carica Salvataggio");

        newGameBtn.getStyleClass().add("menu-button");
        loadBtnMenu.getStyleClass().add("menu-button");

        newGameBtn.setOnAction(e -> showCharacterCreation());
        loadBtnMenu.setOnAction(e -> loadGame());

        menuRoot = new VBox(10, newGameBtn, loadBtnMenu);
        menuRoot.setStyle(
                "-fx-background-image: url('/images/menu.jfif');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center;" +
                        "-fx-alignment: center;" +
                        "-fx-padding: 20;"
        );
    }

    //Character creation

    private void createCharacterCreation() {
        nameField = new TextField();
        nameField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 15 ? change : null
        ));
        nameError.setStyle("-fx-text-fill: #ff4d4d;");
        nameError.setText("");
        nameField.setPromptText("Scegli il tuo nome");
        nameField.setPrefWidth(200);
        nameField.setMaxWidth(180);
        nameField.setStyle(
                "-fx-background-color: #222;" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: gray;"
        );
        Label title = new Label("Crea Personaggio");
        title.getStyleClass().add("creation-title");

        warriorCard = new Button("Guerriero\nHP: 120\nATK: 15");
        mageCard = new Button("Mago\nHP: 80\nATK: 10");

        warriorCard.getStyleClass().add("class-card");
        mageCard.getStyleClass().add("class-card");
        nameField.setText("");

        selectedClass = CharacterClass.WARRIOR;
        warriorCard.setOnAction(e -> selectClass(CharacterClass.WARRIOR));
        mageCard.setOnAction(e -> selectClass(CharacterClass.MAGE));

        updateClassSelectionUI();

        startGameBtn = new Button("Inizia la sfida");
        startGameBtn.getStyleClass().add("rpg-button");
        startGameBtn.setOnAction(e -> startNewGameWithCharacter());

        HBox classBox = new HBox(20, warriorCard, mageCard);
        classBox.setAlignment(Pos.CENTER);

        characterCreationRoot = new VBox(
                15,
                title,
                nameField,
                nameError,
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
        }
        else {
            mageCard.setStyle(
                    "-fx-background-color: #333;" +
                            "-fx-text-fill: white;" +
                            "-fx-border-color: gold;" +
                            "-fx-border-width: 2;"
            );
        }
    }

    //Interfaccia di gioco

    private void createGameUI() {
        nextRoomBtn = new Button("Prossima stanza");
        saveBtn = new Button("Salva");
        restartBtn = new Button("Torna al menù");
        attackBtn = new Button("Attacca");

        attackBtn.setOnAction(e -> {
            RoomResult result = engine.attack();
            resultInfo.setText(result.getMessage());
            updateUI();
              }
        );
        attackBtn.getStyleClass().add("rpg-button");
        attackBtn.getStyleClass().add("attack-button");
        playerInfo.getStyleClass().add("stats-label");
        roomInfo.getStyleClass().add("stats-label");
        resultInfo.getStyleClass().add("stats-label");

        nextRoomBtn.getStyleClass().add("rpg-button");
        saveBtn.getStyleClass().add("rpg-button");
        restartBtn.getStyleClass().add("rpg-button");

        nextRoomBtn.setOnAction(e -> {
            RoomResult result = engine.nextTurn();
            resultInfo.setText(result.getMessage());
            updateUI();
             }
        );
        saveBtn.setOnAction(e -> engine.saveGame());
        restartBtn.setOnAction(e -> showMenu());

        hpBar.setPrefWidth(200);
        hpBar.setStyle(
                "-fx-accent: linear-gradient(to right, #ff4d4d, #b30000);"
        );

        VBox infoBox = new VBox(5,
                playerInfo,
                hpBar,
                roomInfo,
                resultInfo
        );

        HBox bottomBar = new HBox(20);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.getChildren().addAll(
                nextRoomBtn,
                attackBtn,
                saveBtn,
                restartBtn
        );

        bottomBar.setStyle(
                "-fx-padding: 15;" +
                        "-fx-background-color: #1a1a1a;"
        );

        BorderPane root = new BorderPane();
        root.setCenter(infoBox);
        root.setBottom(bottomBar);

        root.setStyle(
                "-fx-padding: 15;" +
                        "-fx-background-color: #0b0b0b;"
        );
        gameRoot = root;
    }

    private void startNewGame() {
        GameState state = TestGameFactory.createTestGame();
        engine = new GameEngine(state, new JsonSaveRepository());
        showGame();
    }

    private void startNewGameWithCharacter() {
        String name = nameField.getText();
        if (name == null || name.isBlank()) {
            nameError.setText("Devi prima scegliere un nome");
            return;
        }
        currentSaveName = name;
        Player player = new Player(name, selectedClass);
        GameState state = new GameState(
                player,
                DungeonGenerator.generateDungeon(5)
        );
        engine = new GameEngine(
                state,
                new JsonSaveRepository()
        );
        resultInfo.setText("Inizia una nuova avventura!");
        showGame();
    }

    private void loadGame() {
        if (engine == null) {
            engine = new GameEngine(
                    TestGameFactory.createTestGame(),
                    new JsonSaveRepository()
            );
        }

        engine.loadGame(currentSaveName);
        showGame();
    }

    private void showCharacterCreation() {
        nameField.clear();
        nameError.setText("");
        scene.setRoot(characterCreationRoot);
    }

    private void showGame() {
        scene.setRoot(gameRoot);
        uiState = UiState.PLAYING;
        if (!gameStarted) {
            resultInfo.setText("Sei entrato/a nel Dungeon");
            gameStarted = true;
        }
        updateUI();
    }

    private void showMenu() {
        scene.setRoot(menuRoot);
        uiState = UiState.MENU;
    }

    private void playTurn() {
        RoomResult result = engine.nextTurn();
        if (engine == null) return;
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
        if (current != null) {
            roomInfo.setText(current.getType().toString());
        } else {
            roomInfo.setText("-");
        }

        if (engine.isGameOver()) {
            if (engine.isVictory()) {
                resultInfo.setText("Hai vinto!");
            } else {
                resultInfo.setText("Hai perso...");
            }
            uiState = UiState.GAME_OVER;
        }

        double hpRatio = (double) player.getHp() / player.getMaxHp();
        hpBar.setProgress(hpRatio);
        boolean inCombat = engine.isInCombat();
        nextRoomBtn.setDisable(inCombat);
        attackBtn.setVisible(inCombat);
        attackBtn.setManaged(inCombat);
    }
    private enum UiState {
        MENU,
        PLAYING,
        GAME_OVER
    }
}