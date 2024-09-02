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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;

public class IngameController implements Controller {
    private App app;
    private Player player;
    private Game game;
    private final List<Controller> cardControllers = new ArrayList<>();
    private PropertyChangeListener cardListener;
    private PropertyChangeListener topcardListener;
    private PropertyChangeListener currentPlayerListener;

    private PropertyChangeListener bot1Listener;
    private PropertyChangeListener bot2Listener;
    private PropertyChangeListener bot3Listener;
    private Player currentPlayer;
    private GameService gameService;
    private BotService botService;
    private Random random;

    public IngameController(App app, Player player, Game game, Random random) {
        this.app = app;
        this.player = player;
        this.game = game;
        this.random = random;
        this.gameService = new GameService(game);
        this.botService = new BotService(gameService);
    }

    @Override
    public String getTitle() {
        return "Ingame";
    }

    @Override
    public void init() {

    }

    @Override
    public Parent render() throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("view/Ingame.fxml")));

        // Hand Boxes
        final ScrollPane playerHand1 = (ScrollPane) root.lookup("#playerBox");
        final HBox playerHand = (HBox) playerHand1.getContent();
        final AnchorPane botHand = (AnchorPane) root.lookup("#botBox1");
        final AnchorPane botHand2 = (AnchorPane) root.lookup("#botBox2");
        final AnchorPane botHand3 = (AnchorPane) root.lookup("#botBox3");

        // Labels
        final Label PlayerName = (Label) root.lookup("#playerName");
        final Label BotName1 = (Label) root.lookup("#bot1");
        final Label BotName2 = (Label) root.lookup("#bot2");
        final Label BotName3 = (Label) root.lookup("#bot3");
        final Label currentPlayerName = (Label) root.lookup("#currantPlayer");

        // color Buttons
        final Button green = (Button) root.lookup("#greenButton");
        final Button blue = (Button) root.lookup("#blueButton");
        final Button red = (Button) root.lookup("#redButton");
        final Button yellow = (Button) root.lookup("#yellowButton");


        final VBox topcard = (VBox) root.lookup("#topCard");
        final VBox deck = (VBox) root.lookup("#deck");
        topcard.getChildren().clear();
        deck.getChildren().clear();
        currentPlayer = game.getCurrentPlayer();

        final CardController topCardController = new CardController(app, player, game.getTopcard(), game);
        topCardController.init();
        cardControllers.add(topCardController);
        topcard.getChildren().add(topCardController.render());


        // Player Draw a Card
        if (!isBot(game)) {
            deck.setOnMouseClicked(event -> {
                gameService.drawCard(player, game);
                    if (game.getName().equals("clockwise")) {
                        game.setCurrentPlayer(gameService.nextPlayer(game));
                    } else {
                        game.setCurrentPlayer(gameService.previousPlayer(game));
                    }
            });
        }


        // color Buttons
        if (!isBot(game)) {
            green.setOnMouseClicked(event -> {
                for (Card card : player.getCards()) {
                    if (card.getName() == "wild") {
                        card.setColor("green");
                    }
                }
            });
        }
        if (!isBot(game)) {
            blue.setOnMouseClicked(event -> {
                for (Card card : player.getCards()) {
                    if (card.getName() == "wild") {
                        card.setColor("blue");
                    }
                }
            });
        }
        if (!isBot(game)) {
            red.setOnMouseClicked(event -> {
                for (Card card : player.getCards()) {
                    if (card.getName() == "wild") {
                        card.setColor("red");
                    }
                }
            });
        }

        if (!isBot(game)) {
            yellow.setOnMouseClicked(event -> {
                for (Card card : player.getCards()) {
                    if (card.getName() == "wild") {
                        card.setColor("yellow");
                    }
                }
            });
        }

        // Deck
        Random random = new Random();
        Player deckPlayer = new Player().setName("Deck");
        int randomInt = random.nextInt(game.getDeck().size());
        final CardController deckController = new CardController(app, deckPlayer, game.getDeck().get(randomInt), game);
        deckController.init();
        cardControllers.add(deckController);
        deck.getChildren().add(deckController.render());


        // Player against 1 Bot
        if (game.getPlayers().size() == 2) {
            PlayerName.setText(player.getName() + ": " + player.getCards().size() + " cards");
            BotName1.setText(game.getPlayers().get(1).getName() + ": " + game.getPlayers().get(1).getCards().size() + " cards");
            currentPlayerName.setText(game.getCurrentPlayer().getName() + "'s turn");

            botHand3.getChildren().clear();
            botHand2.getChildren().clear();
            botHand.getChildren().clear();
            playerHand.getChildren().clear();

            for (Card cards : player.getCards()) {
                final CardController cardController = new CardController(app, player, cards, game);
                cardController.init();
                cardControllers.add(cardController);
                playerHand.getChildren().add(cardController.render());
            }
            for (Card cards2 : game.getPlayers().get(1).getCards()) {
                final CardController cardController2 = new CardController(app, game.getPlayers().get(1), cards2, game);
                cardController2.init();
                cardControllers.add(cardController2);
                botHand.getChildren().add(cardController2.render());
            }
        }

        // Player against 2 Bots
        if (game.getPlayers().size() == 3) {
            PlayerName.setText(player.getName() + ": " + player.getCards().size() + " cards");
            BotName1.setText(game.getPlayers().get(1).getName() + ": " + game.getPlayers().get(1).getCards().size() + " cards");
            BotName2.setText(game.getPlayers().get(2).getName() + ": " + game.getPlayers().get(2).getCards().size() + " cards");
            currentPlayerName.setText(game.getCurrentPlayer().getName() + "'s turn");
            botHand3.getChildren().clear();
            botHand2.getChildren().clear();
            botHand.getChildren().clear();
            playerHand.getChildren().clear();

            for (Card cards : player.getCards()) {
                final CardController cardController = new CardController(app, player, cards, game);
                cardController.init();
                cardControllers.add(cardController);
                playerHand.getChildren().add(cardController.render());
            }
            for (Card cards2 : game.getPlayers().get(1).getCards()) {
                final CardController cardController2 = new CardController(app, game.getPlayers().get(1), cards2, game);
                cardController2.init();
                cardControllers.add(cardController2);
                botHand.getChildren().add(cardController2.render());
            }
            for (Card cards3 : game.getPlayers().get(2).getCards()) {
                final CardController cardController3 = new CardController(app, game.getPlayers().get(2), cards3, game);
                cardController3.init();
                cardControllers.add(cardController3);
                botHand2.getChildren().add(cardController3.render());
            }
        }

        // Player against 3 Bots
        if (game.getPlayers().size() == 4) {
            PlayerName.setText(player.getName() + ": " + player.getCards().size() + " cards");
            BotName1.setText(game.getPlayers().get(1).getName() + ": " + game.getPlayers().get(1).getCards().size() + " cards");
            BotName2.setText(game.getPlayers().get(2).getName() + ": " + game.getPlayers().get(2).getCards().size() + " cards");
            BotName3.setText(game.getPlayers().get(3).getName() + ": " + game.getPlayers().get(3).getCards().size() + " cards");
            currentPlayerName.setText(game.getCurrentPlayer().getName() + "'s turn");
            botHand3.getChildren().clear();
            botHand2.getChildren().clear();
            botHand.getChildren().clear();
            playerHand.getChildren().clear();

            for (Card cards : player.getCards()) {
                final CardController cardController = new CardController(app, player, cards, game);
                cardController.init();
                cardControllers.add(cardController);
                playerHand.getChildren().add(cardController.render());
            }
            for (Card cards2 : game.getPlayers().get(1).getCards()) {
                final CardController cardController2 = new CardController(app, game.getPlayers().get(1), cards2, game);
                cardController2.init();
                cardControllers.add(cardController2);
                botHand.getChildren().add(cardController2.render());
            }
            for (Card cards3 : game.getPlayers().get(2).getCards()) {
                final CardController cardController3 = new CardController(app, game.getPlayers().get(2), cards3, game);
                cardController3.init();
                cardControllers.add(cardController3);
                botHand2.getChildren().add(cardController3.render());
            }
            for (Card cards4 : game.getPlayers().get(3).getCards()) {
                final CardController cardController4 = new CardController(app, game.getPlayers().get(3), cards4, game);
                cardController4.init();
                cardControllers.add(cardController4);
                botHand3.getChildren().add(cardController4.render());
            }
        }

        // Current Player Listener
        currentPlayerListener = e -> {
            Player currentPlayer = (Player) e.getNewValue();
            currentPlayerName.setText(currentPlayer.getName() + "'s turn");
            currentPlayer.listeners().addPropertyChangeListener(Game.PROPERTY_CURRENT_PLAYER, currentPlayerListener);
            if (isBot(game)) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            botService.playCard(game, currentPlayer);
                        });
                    }
                }, 1500);
            }
        };

        // Card Listener
        cardListener = e -> {
            if (!isBot(game)) {
                playerHand.getChildren().clear();
                PlayerName.setText(player.getName() + ": " + player.getCards().size() + " cards");
                currentPlayerName.setText(game.getCurrentPlayer().getName() + "'s turn");
                if (game.getPlayers().get(0).getCards().size() == 0) {
                    app.show(new GameOverController(app, player.getName() + " Won!", random));
                }
                for (Card cards : player.getCards()) {
                    final CardController cardController = new CardController(app, player, cards, game);
                    cardController.init();
                    cardControllers.add(cardController);
                    try {
                        playerHand.getChildren().add(cardController.render());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        };

        //topcard Listener
        topcardListener = e -> {
            topcard.getChildren().clear();
            CardController topCardController1 = new CardController(app, player, game.getTopcard(), game);
            topCardController1.init();
            cardControllers.add(topCardController1);
            try {
                topcard.getChildren().add(topCardController1.render());

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        };

        // Bot1 Listener
        if (game.getPlayers().size() == 2) {
            bot1Listener = e -> {
                botHand.getChildren().clear();
                BotName1.setText(game.getPlayers().get(1).getName() + ": " + game.getPlayers().get(1).getCards().size() + " cards");
                if (game.getPlayers().get(1).getCards().size() == 0) {
                    app.show(new GameOverController(app, "Bot1 Won!", random));
                }
                for (Card cards2 : game.getPlayers().get(1).getCards()) {
                    final CardController cardController2 = new CardController(app, game.getPlayers().get(1), cards2, game);
                    cardController2.init();
                    cardControllers.add(cardController2);
                    try {
                        botHand.getChildren().add(cardController2.render());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };
        }

        // Bot2 Listener
        if (game.getPlayers().size() == 3) {

            bot1Listener = e -> {
                botHand.getChildren().clear();
                BotName1.setText(game.getPlayers().get(1).getName() + ": " + game.getPlayers().get(1).getCards().size() + " cards");
                if (game.getPlayers().get(1).getCards().size() == 0) {
                    app.show(new GameOverController(app, "Bot1 Won!", random));
                }
                for (Card cards2 : game.getPlayers().get(1).getCards()) {
                    final CardController cardController2 = new CardController(app, game.getPlayers().get(1), cards2, game);
                    cardController2.init();
                    cardControllers.add(cardController2);
                    try {
                        botHand.getChildren().add(cardController2.render());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };

            bot2Listener = e -> {
                botHand2.getChildren().clear();
                BotName2.setText(game.getPlayers().get(2).getName() + ": " + game.getPlayers().get(2).getCards().size() + " cards");
                if (game.getPlayers().get(2).getCards().size() == 0) {
                    app.show(new GameOverController(app, "Bot2 Won!", random));
                }
                for (Card cards3 : game.getPlayers().get(2).getCards()) {
                    final CardController cardController3 = new CardController(app, game.getPlayers().get(2), cards3, game);
                    cardController3.init();
                    cardControllers.add(cardController3);
                    try {
                        botHand2.getChildren().add(cardController3.render());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };
        }

        // Bot3 Listener
        if (game.getPlayers().size() == 4) {

            bot1Listener = e -> {
                botHand.getChildren().clear();
                BotName1.setText(game.getPlayers().get(1).getName() + ": " + game.getPlayers().get(1).getCards().size() + " cards");
                if (game.getPlayers().get(1).getCards().size() == 0) {
                    app.show(new GameOverController(app, "Bot1 Won!", random));
                }
                for (Card cards2 : game.getPlayers().get(1).getCards()) {
                    final CardController cardController2 = new CardController(app, game.getPlayers().get(1), cards2, game);
                    cardController2.init();
                    cardControllers.add(cardController2);
                    try {
                        botHand.getChildren().add(cardController2.render());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };

            bot2Listener = e -> {
                botHand2.getChildren().clear();
                BotName2.setText(game.getPlayers().get(2).getName() + ": " + game.getPlayers().get(2).getCards().size() + " cards");
                if (game.getPlayers().get(2).getCards().size() == 0) {
                    app.show(new GameOverController(app, "Bot2 Won!", random));
                }
                for (Card cards3 : game.getPlayers().get(2).getCards()) {
                    final CardController cardController3 = new CardController(app, game.getPlayers().get(2), cards3, game);
                    cardController3.init();
                    cardControllers.add(cardController3);
                    try {
                        botHand2.getChildren().add(cardController3.render());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };

            bot3Listener = e -> {
                botHand3.getChildren().clear();
                BotName3.setText(game.getPlayers().get(3).getName() + ": " + game.getPlayers().get(3).getCards().size() + " cards");
                if (game.getPlayers().get(3).getCards().size() == 0) {
                    app.show(new GameOverController(app, "Bot3 Won!", random));
                }
                for (Card cards3 : game.getPlayers().get(2).getCards()) {
                    final CardController cardController3 = new CardController(app, game.getPlayers().get(2), cards3, game);
                    cardController3.init();
                    cardControllers.add(cardController3);
                    try {
                        botHand3.getChildren().add(cardController3.render());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };

        }


        // subscribe Listeners
        if (game.getPlayers().size() == 3) {
            game.getPlayers().get(2).listeners().addPropertyChangeListener(Player.PROPERTY_CARDS, bot2Listener);
        }
        if (game.getPlayers().size() == 4) {
            game.getPlayers().get(2).listeners().addPropertyChangeListener(Player.PROPERTY_CARDS, bot2Listener);
            game.getPlayers().get(3).listeners().addPropertyChangeListener(Player.PROPERTY_CARDS, bot3Listener);
        }
        game.getPlayers().get(1).listeners().addPropertyChangeListener(Player.PROPERTY_CARDS, bot1Listener);
        game.listeners().addPropertyChangeListener(Game.PROPERTY_CURRENT_PLAYER, currentPlayerListener);
        game.listeners().addPropertyChangeListener(Game.PROPERTY_TOPCARD, topcardListener);
        currentPlayer.listeners().addPropertyChangeListener(Player.PROPERTY_CARDS, cardListener);

        return root;
    }


    @Override
    public void destroy() {
        game.getPlayers().get(3).listeners().removePropertyChangeListener(Player.PROPERTY_CARDS, bot3Listener);
        game.getPlayers().get(2).listeners().removePropertyChangeListener(Player.PROPERTY_CARDS, bot2Listener);
        game.getPlayers().get(1).listeners().removePropertyChangeListener(Player.PROPERTY_CARDS, bot1Listener);
        game.listeners().removePropertyChangeListener(Game.PROPERTY_CURRENT_PLAYER, currentPlayerListener);
        game.listeners().removePropertyChangeListener(Game.PROPERTY_TOPCARD, topcardListener);
        currentPlayer.listeners().removePropertyChangeListener(Player.PROPERTY_CARDS, cardListener);
        for (Controller cardController : cardControllers) {
            cardController.destroy();
        }
    }

    public Boolean isBot(Game game) {
        if (game.getCurrentPlayer().getName() == "Bot1" || game.getCurrentPlayer().getName() == "Bot2"
                || game.getCurrentPlayer().getName() == "Bot3") {
            return true;
        } else {
            return false;
        }
    }

}
