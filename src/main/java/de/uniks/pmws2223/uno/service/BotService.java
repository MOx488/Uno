package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class BotService {
    private GameService gameService;

    public BotService(GameService gameService) {
        this.gameService = gameService;
    }

    public void playCard(Game game, Player player) {
        if (gameService.isBot(game) && game.getCurrentPlayer() == player) {
            int c = 0;
            for (Card card : player.getCards()) {
                if (gameService.checkCard(card, game)) {
                    c++;

                        if (game.getName().equals("clockwise")) {
                            game.setCurrentPlayer(gameService.nextPlayer(game));
                        } else {
                            game.setCurrentPlayer(gameService.previousPlayer(game));
                        }

                    gameService.playCardGame(player, card, game);

                    break;
                }
            }
            if (c == 0) {
                gameService.drawCard(player, game);
            }
        }
    }
}