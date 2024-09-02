package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;

import java.util.Random;


public class GameService {
    private final Game game;

    public GameService(Game game) {
        this.game = game;
    }

    public boolean checkCard(Card card, Game game) {
        if (game.getTopcard().getColor().equals(card.getColor()) || game.getTopcard().getName().equals(card.getName())
                || card.getName().equals("wild")) {
            return true;
        } else {
            return false;
        }
    }

    public void playCard(Player player, Card card, Game game) {
        if (isHuman()) {
            if (checkCard(card, game)) {
                playCardGame(player, card, game);
                if (game.getPlayers().size() == 2) {
                    if (game.getName().equals("clockwise")) {
                        game.setCurrentPlayer(nextPlayer(game));
                    } else {
                        game.setCurrentPlayer(previousPlayer(game));
                    }
                } else if (card.getName() != "reverse") {
                    if (game.getName().equals("clockwise")) {
                        game.setCurrentPlayer(nextPlayer(game));
                    } else {
                        game.setCurrentPlayer(previousPlayer(game));
                    }
                }
            } else {
                System.out.println("Card not playable");
            }
        }
    }

    public void playCardGame(Player player, Card card, Game game) {
        if (checkCard(card, game)) {
            if (isSpecialCard(card)) {
                specialCard(card, game, player);
            } else {
                game.setTopcard(card);
                player.withoutCards(card);
            }

        }
        if (isBot(game) && game.getCurrentPlayer() == player && card.getName() != ("draw2") && card.getName() != ("reverse")) {
            if (game.getName().equals("clockwise")) {
                game.setCurrentPlayer(nextPlayer(game));
            } else {
                game.setCurrentPlayer(previousPlayer(game));
            }
        }


    }


    public void drawCard(Player player, Game game) {
        Card card = new Card();
        Random random = new Random();
        int r = random.nextInt(game.getDeck().size());
        card.setColor(game.getDeck().get(r).getColor());
        card.setName(game.getDeck().get(r).getName());
        if (player.getCards().contains(card)) {
            player.withCards(new Card().setColor(card.getColor()).setName(card.getName()));
        } else {
            player.withCards(card);
        }
        if (card.getName() == "wild") {
            card.setColor(player.getCards().get(0).getColor());
        }
        if (!checkCard(card, game)) {
            if (card.getName() == ("draw2") || card.getName() == ("reverse")) {
                if (game.getName().equals("clockwise")) {
                    game.setCurrentPlayer(nextPlayer(game));
                } else {
                    game.setCurrentPlayer(previousPlayer(game));
                }
            }
        }
        playCardGame(player, card, game);

    }

    public Player nextPlayer(Game game) {
        game.setName("clockwise");
        int index = game.getPlayers().indexOf(game.getCurrentPlayer());
        if (index == game.getPlayers().size() - 1) {
            return game.getPlayers().get(0);
        } else {
            return game.getPlayers().get(index + 1);
        }
    }

    public Player previousPlayer(Game game) {
        game.setName("counter-clockwise");
        int index = game.getPlayers().indexOf(game.getCurrentPlayer());
        if (index == 0) {
            game.setCurrentPlayer(game.getPlayers().get(game.getPlayers().size() - 1));
        } else {
            game.setCurrentPlayer(game.getPlayers().get(index - 1));
        }
        return game.getCurrentPlayer();
    }

    public void specialCard(Card card, Game game, Player player) {
        // skip
        if (card.getName().equals("skip")) {
            justPlayCard(player, card, game);

            if (game.getName().equals("clockwise")) {
                game.setCurrentPlayer(nextPlayer(game));
                if (!isBot(game)) {
                    game.setCurrentPlayer(nextPlayer(game));
                }
            } else {
                if (!isBot(game)) {
                    game.setCurrentPlayer(previousPlayer(game));
                }
                game.setCurrentPlayer(previousPlayer(game));
            }
            // reverse
        } else if (card.getName().equals("reverse")) {
            justPlayCard(player, card, game);
            if (game.getName().equals("clockwise")) {
                game.setCurrentPlayer(previousPlayer(game));
            } else {
                game.setCurrentPlayer(nextPlayer(game));
            }
            // draw2
        } else if (card.getName().equals("draw2")) {
            justPlayCard(player, card, game);
            if (!playerIsBot(player)) {
                if (game.getName().equals("clockwise")) {
                    drwa2(nextPlayer(game), game);
                    game.setCurrentPlayer(nextPlayer(game));
                } else {
                    game.setName("counter-clockwise");
                    drwa2(previousPlayer(game), game);
                    game.setCurrentPlayer(previousPlayer(game));
                }
            } else {
                if (game.getName().equals("clockwise")) {
                    drwa2(game.getCurrentPlayer(), game);
                } else {
                    game.setName("counter-clockwise");
                    drwa2(game.getCurrentPlayer(), game);
                }
                if (game.getName().equals("clockwise")) {
                    game.setCurrentPlayer(nextPlayer(game));

                } else {
                    game.setCurrentPlayer(previousPlayer(game));

                }
            }
            // wild
        } else if (card.getName().equals("wild")) {
            if (card.getColor() == "black") {
                card.setColor("red");
            }
            if (playerIsBot(player)) {
                card.setColor("red");
            }
            justPlayCard(player, card, game);

        }
    }

    public boolean isSpecialCard(Card card) {
        if (card.getName().equals("skip") || card.getName().equals("reverse") || card.getName().equals("draw2")
                || card.getName().equals("wild")) {
            return true;
        } else {
            return false;
        }
    }

    public void justPlayCard(Player player, Card card, Game game) {
        if (checkCard(card, game)) {
            game.setTopcard(card);
            player.withoutCards(card);
        }
    }

    public void drwa2(Player player, Game game) {
        Card card = new Card();
        Random random = new Random();
        int r = random.nextInt(game.getDeck().size());
        for (int i = 0; i < 2; i++) {
            card.setColor(game.getDeck().get(r).getColor());
            card.setName(game.getDeck().get(r).getName());
            if (player.getCards().contains(card)) {
                player.withCards(new Card().setColor(card.getColor()).setName(card.getName()));
            } else {
                player.withCards(card);
            }
        }
    }

    public boolean isBot(Game game) {
        if (game.getCurrentPlayer().getName() == "Bot1" || game.getCurrentPlayer().getName() == "Bot2" ||
                game.getCurrentPlayer().getName() == "Bot3") {
            return true;
        } else {
            return false;
        }
    }

    public boolean playerIsBot(Player player) {
        if (player.getName() == "Bot1" || player.getName() == "Bot2" ||
                player.getName() == "Bot3") {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHuman() {
        return game.getCurrentPlayer().getName() != "Bot1" && game.getCurrentPlayer().getName() != "Bot2"
                && game.getCurrentPlayer().getName() != "Bot3";
    }


}

