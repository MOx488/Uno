package de.uniks.pmws2223.uno.controller;

import de.uniks.pmws2223.uno.App;
import de.uniks.pmws2223.uno.Main;
import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;
import de.uniks.pmws2223.uno.service.BotService;
import de.uniks.pmws2223.uno.service.GameService;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class CardController implements Controller {

    private Player player;
    private Card card;
    private Game game;
    private GameService gameService;
    private BotService botService;
    private final App app;

    public CardController(App app, Player player, Card card, Game game) {
        this.app = app;
        this.player = player;
        this.card = card;
        this.game = game;
        this.gameService = new GameService(game);
        this.botService = new BotService(gameService);
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
        final Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("view/Card.fxml")));
        final VBox imageBox = (VBox) root.lookup("#imageBox");

        imageBox.setOnMouseClicked(event -> {
            if (isHuman()) {
                if (game.getCurrentPlayer().equals(player)) {
                    playCard();
                }
            }
        });
        final ImageView cardimage = (ImageView) root.lookup("#playerCard");

        if (game.getTopcard().getName() == "wild") {
            Card card = game.getTopcard();
            if (game.getTopcard().getColor() == "red") {
                FileInputStream inputstream = new FileInputStream("src/main/resources/de/uniks/pmws2223/Images/" + card.getColor() + card.getName() + ".png");
                Image image = new Image(inputstream);
                cardimage.setImage(image);
                game.setTopcard(card);
            }
        }

        // Cards for the player
        if (isPlayerAndDeck()) {
            FileInputStream inputstream = new FileInputStream("src/main/resources/de/uniks/pmws2223/Images/" + card.getColor() + card.getName() + ".png");
            Image image = new Image(inputstream);
            cardimage.setImage(image);
        } else {
            // Cards for the bots
            FileInputStream inputstream = new FileInputStream("src/main/resources/de/uniks/pmws2223/Images/" + "faceddown" + ".png");
            Image image = new Image(inputstream);
            cardimage.setImage(image);
            cardimage.setFitHeight(103);
            cardimage.setFitWidth(73);
        }


        return root;
    }

    public void playCard() {
        gameService.playCard(player, card, game);
    }

    @Override
    public void destroy() {

    }

    public boolean isHuman() {
        return game.getCurrentPlayer().getName() != "Bot1" && game.getCurrentPlayer().getName() != "Bot2"
                && game.getCurrentPlayer().getName() != "Bot3";
    }

    public boolean isPlayerAndDeck() {
        return player.getName() != "Bot1" && player.getName() != "Bot2"
                && player.getName() != "Bot3" && player.getName() != "Deck";
    }

}