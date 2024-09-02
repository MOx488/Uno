package de.uniks.pmws2223.uno.model;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.builder.reflect.Link;

import java.util.List;

public class GenModel implements ClassModelDecorator {
    public class Card {
        String color;
        String name;

        @Link("cards")
        List<Player> players;
        @Link("topcard")
        Game game;
    }

    public class Player {
        String name;
        @Link("players")
        List<Card> cards;
        @Link("players")
        Game game;
        @Link("player")
        Player player;

    }

    public class Game {
        Player currentPlayer;
        List<Card> deck;
        @Link("game")
        List<Player> players;
        @Link("game")
        Card topcard;
    }

    @Override
    public void decorate(ClassModelManager mm) {
        mm.haveNestedClasses(GenModel.class);

    }
}
