package de.uniks.pmws2223.uno.controller;

import de.uniks.pmws2223.uno.App;
import de.uniks.pmws2223.uno.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class GameOverController implements Controller {
    private final App app;
    private final String winner;
    Random random;

    public GameOverController(App app, String winner, Random random) {
        this.app = app;
        this.winner = winner;
        this.random = random;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public Parent render() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("view/GameOver.fxml")));
        final Label winner = (Label) root.lookup("#result");
        final Button restart = (Button) root.lookup("#setupButton");
        winner.setText(this.winner);
        restart.setOnAction(event -> {
            app.show(new SetupController(app, random));
        });


        return root;
    }

    @Override
    public void destroy() {

    }
}
