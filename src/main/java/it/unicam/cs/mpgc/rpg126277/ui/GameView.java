package it.unicam.cs.mpgc.rpg126277.ui;



import it.unicam.cs.mpgc.rpg126277.core.GameEngine;
import it.unicam.cs.mpgc.rpg126277.model.GameState;
import it.unicam.cs.mpgc.rpg126277.world.RoomResult;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameView extends Application {

    private GameEngine engine;
    private Button nextRoomBtn;
    private Button saveBtn;
    private Button loadBtn;
    private Button restartBtn;

    private Label playerInfo = new Label();
    private Label roomInfo = new Label();
    private Label resultInfo = new Label();

    @Override
    public void start(Stage stage) {
        GameState state = TestGameFactory.createTestGame();
        engine = new GameEngine(state);

        restartBtn = new Button("New Game");
        nextRoomBtn = new Button("Next Room");
        saveBtn = new Button("Save");
        loadBtn = new Button("Load");

        restartBtn.setOnAction(e -> {
            GameState newState = TestGameFactory.createTestGame();
            engine = new GameEngine(newState);
            engine.setGameStarted(true);
            resultInfo.setText("New Game Started!");

            resultInfo.setText("");
            updateUI();
        });

        nextRoomBtn.setOnAction(e -> playTurn());

        saveBtn.setOnAction(e -> engine.saveGame());

        loadBtn.setOnAction(e -> {
            engine.loadGame("Test");

            engine.setGameStarted(true);

            updateUI();
        });

        VBox root = new VBox(10,
                playerInfo,
                roomInfo,
                resultInfo,
                nextRoomBtn,
                saveBtn,
                loadBtn,
                restartBtn
        );

        updateUI();

        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("RPG Dungeon Crawler");
        stage.show();
    }

    private void playTurn() {
        if (engine.isGameOver()) {
            if (engine.isVictory()) {
                resultInfo.setText("YOU WIN!");
            } else {
                resultInfo.setText("GAME OVER");
            }
            return;
        }

        RoomResult result = engine.nextTurn();

        resultInfo.setText(result.getMessage());
        updateUI();
    }
    private void updateButtons() {
        boolean finished = engine.isGameOver();
        boolean started = engine.isGameStarted();

        nextRoomBtn.setDisable(!started || finished);
        saveBtn.setDisable(!started || finished);

        loadBtn.setDisable(false);
        restartBtn.setDisable(false);
    }

    private void updateUI() {
        var state = engine.getGameState();
        var player = state.getPlayer();

        playerInfo.setText(
                "Player: " + player.getName() +
                        " HP: " + player.getHp() +
                        " LV: " + player.getLevel()
        );

        roomInfo.setText("Room: " + state.getCurrentRoomIndex());

        if (engine.isGameOver()) {
            if (engine.isVictory()) {
                resultInfo.setText("🏆 YOU WIN!");
            } else {
                resultInfo.setText("☠ GAME OVER");
            }
        }

        updateButtons();
    }
}
