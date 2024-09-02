package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class BotServiceTest {
    @Test
    // Test if same color is playable
    public void botPlayCard() {
        //select specific Random
        Random random = new Random(8041983);
        //generate specific Random
        RandomGenerator randomGenerator = new RandomGenerator(random);
        //create Game with specific Deck and Topcard
        final Game game = new Game().withDeck(randomGenerator.createDeck()).
                setTopcard(randomGenerator.createCard()).setName("clockwise");
        assertTrue(game.getTopcard().getName().equals("5") && game.getTopcard().getColor().equals("green"));
        //create GameService
        GameService gameService = new GameService(game);
        //create BotService
        BotService botService = new BotService(gameService);
        //create Player with specific cards
        Player player = randomGenerator.createPlayer("mo");
        assertTrue(player.getCards().size() == 7);
        //create Bot with specific cards
        Player Bot = randomGenerator.createPlayer("Bot1");
        assertTrue(Bot.getCards().size() == 7);
        //create Bot2 with specific cards
        Player Bot2 = randomGenerator.createPlayer("Bot2");
        assertTrue(Bot2.getCards().size() == 7);
        //add Players to Game
        game.withPlayers(player, Bot, Bot2);
        assertTrue(game.getPlayers().size() == 3);
        //set current Player
        game.setCurrentPlayer(player);
        assertTrue(game.getCurrentPlayer().getName().equals("mo"));
        //print all cards of Player
        for (Card card : player.getCards()) {
            System.out.println(card.getName() + " " + card.getColor());
        }
        System.out.println("Topcard: " + game.getTopcard().getName() + " " + game.getTopcard().getColor());
        //print all cards of Bot
        for (Card card : Bot.getCards()) {
            System.out.println(card.getName() + " " + card.getColor());
        }
        //play same color
        gameService.playCard(player, player.getCards().get(0), game);

        //check if Player has 6 Cards
        assertTrue(player.getCards().size() == 6);
        //check if Topcard is same color
        assertTrue(game.getTopcard().getName().equals("1") && game.getTopcard().getColor().equals("green"));
        //check if current Player is Bot1
        assertTrue(game.getCurrentPlayer().getName().equals("Bot1"));

        //bot plays card
        botService.playCard(game, Bot);

        //check if Bot has 6 Cards
        assertTrue(Bot.getCards().size() == 6);
        //check the Topcard
        assertTrue(game.getTopcard().getName().equals("5") && game.getTopcard().getColor().equals("green"));
        //check if current Player is Bot2
        assertTrue(game.getCurrentPlayer().getName().equals("Bot2"));

    }
}
