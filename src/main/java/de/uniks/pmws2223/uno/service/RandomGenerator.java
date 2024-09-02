package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static de.uniks.pmws2223.uno.Constants.color;
import static de.uniks.pmws2223.uno.Constants.name;
import static java.util.Collections.*;

public class RandomGenerator {

    Random random;

    public RandomGenerator(Random random) {
        this.random = random;
    }

    public Player createPlayer(String playername) {
        Player player = new Player();
        player.setName(playername);
        for (int i = 0; i < 7; i++) {
            Card card = new Card();
            card.setName(name.get(random.nextInt(name.size())));
            if (card.getName().equals("wild")) {
                card.setColor("black");
            } else {
                card.setColor(color.get(random.nextInt(color.size())));
            }
            player.withCards(card);
        }
        return player;
    }

    public List<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                Card card = new Card();
                card.setName(name.get(j));
                if (card.getName().equals("wild")) {
                    card.setColor("black");
                } else {
                    card.setColor(color.get(i));
                }
                deck.add(card);
            }
        }
        shuffle(deck);
        return deck;
    }

    public Card createCard() {
        Card card = new Card();
        card.setName(name.get(random.nextInt(name.size())));
        if (card.getName().equals("wild")) {
            card.setColor("black");
        } else {
            card.setColor(color.get(random.nextInt(color.size())));
        }
        return card;
    }

    public Card createTopCard() {
        Card card = new Card();
        card.setName(name.get(random.nextInt(name.size())));
        if (card.getName().equals("wild")) {
            card.setColor("yellow");
        } else {
            card.setColor(color.get(random.nextInt(color.size())));
        }
        return card;
    }
}
