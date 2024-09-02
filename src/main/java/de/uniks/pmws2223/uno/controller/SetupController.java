package de.uniks.pmws2223.uno.controller;

import de.uniks.pmws2223.uno.App;
import de.uniks.pmws2223.uno.Main;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;
import de.uniks.pmws2223.uno.service.RandomGenerator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class SetupController implements Controller {
    private final App app;
    Random random;
    RandomGenerator randomGenerator;

    public SetupController(App app, Random random) {
        this.app = app;
        this.random = random;
        this.randomGenerator = new RandomGenerator(random);
    }

    @Override
    public String getTitle() {
        return "Setup";
    }

    @Override
    public void init() {

    }

    @Override
    public Parent render() throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("view/Setup.fxml")));
        final Button vs1 = (Button) root.lookup("#button1v1");
        final Button vs2 = (Button) root.lookup("#button1v2");
        final Button vs3 = (Button) root.lookup("#button1v3");


        // 1v1-Button being pressed
        vs1.setOnAction(e -> {
            final TextField nameInput = (TextField) root.lookup("#nameField");
            final String playerName = nameInput.getText();
            final Game game = new Game().withDeck(randomGenerator.createDeck()).
                    setTopcard(randomGenerator.createTopCard()).setName("clockwise");
            Player player = randomGenerator.createPlayer(playerName);
            Player Bot = randomGenerator.createPlayer("Bot1");
            game.withPlayers(player, Bot);
            game.setCurrentPlayer(player);
            app.show(new IngameController(app, player, game, random));
        });

        // 1v2-Button being pressed
        vs2.setOnAction(e -> {
            final TextField nameInput = (TextField) root.lookup("#nameField");
            final String playerName = nameInput.getText();
            final Game game = new Game().withDeck(randomGenerator.createDeck()).
                    setTopcard(randomGenerator.createTopCard()).setName("clockwise");
            Player player = randomGenerator.createPlayer(playerName);
            Player Bot = randomGenerator.createPlayer("Bot1");
            Player Bot2 = randomGenerator.createPlayer("Bot2");
            game.withPlayers(player, Bot, Bot2);
            game.setCurrentPlayer(player);
            app.show(new IngameController(app, player, game, random));
        });

        // 1v3-Button being pressed
        vs3.setOnAction(e -> {
            final TextField nameInput = (TextField) root.lookup("#nameField");
            final String playerName = nameInput.getText();
            final Game game = new Game().withDeck(randomGenerator.createDeck()).
                    setTopcard(randomGenerator.createTopCard()).setName("clockwise");
            Player player = randomGenerator.createPlayer(playerName);
            Player Bot = randomGenerator.createPlayer("Bot1");
            Player Bot2 = randomGenerator.createPlayer("Bot2");
            Player Bot3 = randomGenerator.createPlayer("Bot3");
            game.withPlayers(player, Bot, Bot2, Bot3);
            game.setCurrentPlayer(player);
            app.show(new IngameController(app, player, game, random));
        });

        return root;
    }

    @Override
    public void destroy() {

    }
}
