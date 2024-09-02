package de.uniks.pmws2223.uno.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

public class Game
{
   public static final String PROPERTY_PLAYERS = "players";
   public static final String PROPERTY_TOPCARD = "topcard";
   public static final String PROPERTY_DECK = "deck";
   public static final String PROPERTY_CURRENT_PLAYER = "currentPlayer";
   private List<Player> players;
   private Card topcard;

   public String getName() {
      return name;
   }

   public Game setName(String name) {
      this.name = name;
      return this;
   }

   private String name;

   public Player getCurrentPlayer()
   {
      return this.currentPlayer;
   }

   public Game setCurrentPlayer(Player value)
   {
      if (Objects.equals(value, this.currentPlayer))
      {
         return this;
      }

      final Player oldValue = this.currentPlayer;
      this.currentPlayer = value;
      this.firePropertyChange(PROPERTY_CURRENT_PLAYER, oldValue, value);
      return this;
   }

   private Player currentPlayer;
   protected PropertyChangeSupport listeners;
   private List<Card> deck;

   public List<Player> getPlayers()
   {
      return this.players != null ? Collections.unmodifiableList(this.players) : Collections.emptyList();
   }

   public Game withPlayers(Player value)
   {
      if (this.players == null)
      {
         this.players = new ArrayList<>();
      }
      if (!this.players.contains(value))
      {
         this.players.add(value);
         value.setGame(this);
         this.firePropertyChange(PROPERTY_PLAYERS, null, value);
      }
      return this;
   }

   public Game withPlayers(Player... value)
   {
      for (final Player item : value)
      {
         this.withPlayers(item);
      }
      return this;
   }

   public Game withPlayers(Collection<? extends Player> value)
   {
      for (final Player item : value)
      {
         this.withPlayers(item);
      }
      return this;
   }

   public Game withoutPlayers(Player value)
   {
      if (this.players != null && this.players.remove(value))
      {
         value.setGame(null);
         this.firePropertyChange(PROPERTY_PLAYERS, value, null);
      }
      return this;
   }

   public Game withoutPlayers(Player... value)
   {
      for (final Player item : value)
      {
         this.withoutPlayers(item);
      }
      return this;
   }

   public Game withoutPlayers(Collection<? extends Player> value)
   {
      for (final Player item : value)
      {
         this.withoutPlayers(item);
      }
      return this;
   }

   public Card getTopcard()
   {
      return this.topcard;
   }

   public Game setTopcard(Card value)
   {
      if (this.topcard == value)
      {
         return this;
      }

      final Card oldValue = this.topcard;
      if (this.topcard != null)
      {
         this.topcard = null;
         oldValue.setGame(null);
      }
      this.topcard = value;
      if (value != null)
      {
         value.setGame(this);
      }
      this.firePropertyChange(PROPERTY_TOPCARD, oldValue, value);
      return this;
   }

   public List<Card> getDeck()
   {
      return this.deck != null ? Collections.unmodifiableList(this.deck) : Collections.emptyList();
   }

   public Game withDeck(Card value)
   {
      if (this.deck == null)
      {
         this.deck = new ArrayList<>();
      }
      if (this.deck.add(value))
      {
         this.firePropertyChange(PROPERTY_DECK, null, value);
      }
      return this;
   }

   public Game withDeck(Card... value)
   {
      for (final Card item : value)
      {
         this.withDeck(item);
      }
      return this;
   }

   public Game withDeck(Collection<? extends Card> value)
   {
      for (final Card item : value)
      {
         this.withDeck(item);
      }
      return this;
   }

   public Game withoutDeck(Card value)
   {
      if (this.deck != null && this.deck.removeAll(Collections.singleton(value)))
      {
         this.firePropertyChange(PROPERTY_DECK, value, null);
      }
      return this;
   }

   public Game withoutDeck(Card... value)
   {
      for (final Card item : value)
      {
         this.withoutDeck(item);
      }
      return this;
   }

   public Game withoutDeck(Collection<? extends Card> value)
   {
      for (final Card item : value)
      {
         this.withoutDeck(item);
      }
      return this;
   }

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (this.listeners != null)
      {
         this.listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public PropertyChangeSupport listeners()
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      return this.listeners;
   }

   public void removeYou()
   {
      this.withoutPlayers(new ArrayList<>(this.getPlayers()));
      this.setTopcard(null);
   }
}
