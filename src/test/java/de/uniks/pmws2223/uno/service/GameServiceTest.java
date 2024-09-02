package de.uniks.pmws2223.uno.service;


import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class GameServiceTest {
    @Test
    // Test if reverse is playable
    public void playRevers() {
        //select random
        Random random = new Random(8041998);
        RandomGenerator randomGenerator = new RandomGenerator(random);//generate specific Random
        //create Game with specific Deck and Topcard
        final Game game = new Game().withDeck(randomGenerator.createDeck()).
                setTopcard(randomGenerator.createCard()).setName("clockwise");
        assertTrue(game.getTopcard().getName().equals("2") && game.getTopcard().getColor().equals("yellow"));
        //create GameService
        GameService gameService = new GameService(game);
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
        //play reverse
        gameService.playCardGame(player, player.getCards().get(1), game);

        //check if player has 6 cards
        assertTrue(player.getCards().size() == 6);
        //check if topcard is reverse
        assertTrue(game.getTopcard().getName().equals("reverse") && game.getTopcard().getColor().equals("yellow"));
        //check if current Player is Bot2
        assertTrue(game.getCurrentPlayer().getName().equals("Bot2"));
    }

    @Test
    // Test if Draw2 is playable
    public void playDraw2() {
        //select specific Random
        Random random = new Random(2777777);
        //generate specific Random
        RandomGenerator randomGenerator = new RandomGenerator(random);
        //create Game with specific Deck and Topcard
        final Game game = new Game().withDeck(randomGenerator.createDeck()).
                setTopcard(randomGenerator.createCard()).setName("clockwise");
        assertTrue(game.getTopcard().getName().equals("8") && game.getTopcard().getColor().equals("green"));
        //create GameService
        GameService gameService = new GameService(game);
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
        //play Draw2
        gameService.playCard(player, player.getCards().get(2), game);

        //check if Player has 9 Cards
        assertTrue(Bot.getCards().size() == 9);
        //check if Player has 6 Cards
        assertTrue(player.getCards().size() == 6);
        //check if Topcard is Draw2
        assertTrue(game.getTopcard().getName().equals("draw2") && game.getTopcard().getColor().equals("green"));
        //check if current Player is Bot2
        assertTrue(game.getCurrentPlayer().getName().equals("Bot2"));


    }

    @Test
    // Test if Wild is playable
    public void playWild() {
        //select specific Random
        Random random = new Random(8041997);
        //generate specific Random
        RandomGenerator randomGenerator = new RandomGenerator(random);
        //create Game with specific Deck and Topcard
        final Game game = new Game().withDeck(randomGenerator.createDeck()).
                setTopcard(randomGenerator.createCard()).setName("clockwise");
        assertTrue(game.getTopcard().getName().equals("1") && game.getTopcard().getColor().equals("green"));
        //create GameService
        GameService gameService = new GameService(game);
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
        //play Wild
        gameService.playCard(player, player.getCards().get(0), game);

        //check if Player has 6 Cards
        assertTrue(player.getCards().size() == 6);
        //check if Topcard is Wild
        assertTrue(game.getTopcard().getName().equals("wild") && game.getTopcard().getColor().equals("red"));
        //check if current Player is Bot1
        assertTrue(game.getCurrentPlayer().getName().equals("Bot1"));
    }

    @Test
    // Test if skip is playable
    public void playSkip() {
        //select specific Random
        Random random = new Random(8041989);
        //generate specific Random
        RandomGenerator randomGenerator = new RandomGenerator(random);
        //create Game with specific Deck and Topcard
        final Game game = new Game().withDeck(randomGenerator.createDeck()).
                setTopcard(randomGenerator.createCard()).setName("clockwise");
        assertTrue(game.getTopcard().getName().equals("4") && game.getTopcard().getColor().equals("blue"));
        //create GameService
        GameService gameService = new GameService(game);
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
        //play Skip
        gameService.playCard(player, player.getCards().get(3), game);

        //check if Player has 6 Cards
        assertTrue(player.getCards().size() == 6);
        //check if Topcard is Skip
        assertTrue(game.getTopcard().getName().equals("skip") && game.getTopcard().getColor().equals("blue"));
        //check if current Player is Bot2
        assertTrue(game.getCurrentPlayer().getName().equals("Bot2"));
    }

    @Test
    // Test if same number is playable
    public void playSameNum() {
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
        //create Player with specific cards
        Player player = randomGenerator.createPlayer("mo");
        assertTrue(player.getCards().size() == 7);
        //create Bot with specific cards
        Player Bot = randomGenerator.createPlayer("Bot1");
        assertTrue(Bot.getCards().size() == 7);
        //create Bo2 with specific cards
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
        //play same number
        gameService.playCard(player, player.getCards().get(5), game);

        //check if Player has 6 Cards
        assertTrue(player.getCards().size() == 6);
        //check if Topcard is same number
        assertTrue(game.getTopcard().getName().equals("5") && game.getTopcard().getColor().equals("blue"));
        //check if current Player is Bot1
        assertTrue(game.getCurrentPlayer().getName().equals("Bot1"));
    }

    @Test
    // Test if same color is playable
    public void playSameColor() {
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
        //play same color
        gameService.playCard(player, player.getCards().get(0), game);

        //check if Player has 6 Cards
        assertTrue(player.getCards().size() == 6);
        //check if Topcard is same color
        assertTrue(game.getTopcard().getName().equals("1") && game.getTopcard().getColor().equals("green"));
        //check if current Player is Bot1
        assertTrue(game.getCurrentPlayer().getName().equals("Bot1"));
    }
}
