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

    private Label playerInfo = new Label();
    private Label roomInfo = new Label();
    private Label resultInfo = new Label();

    @Override
    public void start(Stage stage) {

        GameState state = TestGameFactory.createTestGame();
        engine = new GameEngine(state);

        Button nextRoomBtn = new Button("Next Room");

        nextRoomBtn.setOnAction(e -> playTurn());

        VBox root = new VBox(10, playerInfo, roomInfo, resultInfo, nextRoomBtn);

        updateUI();

        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("RPG Dungeon Crawler");
        stage.show();
    }

    private void playTurn() {

        if (engine.isGameOver()) {
            resultInfo.setText("GAME OVER");
            return;
        }

        RoomResult result = engine.playNextRoom();

        updateUI();

        resultInfo.setText(result.getMessage());
    }

    private void updateUI() {
        var state = engine.getGameState();
        var player = state.getPlayer();

        playerInfo.setText(
                "Player: " + player.getName() +
                        " HP: " + player.getHp() +
                        " LV: " + player.getLevel()
        );

        roomInfo.setText(
                "Room: " + state.getCurrentRoomIndex()
        );
    }
}
