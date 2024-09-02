package de.uniks.pmws2223.uno.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;

public class Card {
    public static final String PROPERTY_COLOR = "color";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_GAME = "game";
    public static final String PROPERTY_PLAYERS = "players";
    private String color;
    private String name;
    private Game game;
    private Image image;
    private List<Player> players;
    protected PropertyChangeSupport listeners;

    public String getColor()
   {
      return this.color;
   }

    public Card setColor(String value)
   {
      if (Objects.equals(value, this.color))
      {
         return this;
      }

      final String oldValue = this.color;
      this.color = value;
      this.firePropertyChange(PROPERTY_COLOR, oldValue, value);
      return this;
   }

    public String getName()
   {
      return this.name;
   }

    public Card setName(String value)
   {
      if (Objects.equals(value, this.name))
      {
         return this;
      }

      final String oldValue = this.name;
      this.name = value;
      this.firePropertyChange(PROPERTY_NAME, oldValue, value);
      return this;
   }

    public Game getGame()
   {
      return this.game;
   }

    public Card setGame(Game value)
   {
      if (this.game == value)
      {
         return this;
      }

      final Game oldValue = this.game;
      if (this.game != null)
      {
         this.game = null;
         oldValue.setTopcard(null);
      }
      this.game = value;
      if (value != null)
      {
         value.setTopcard(this);
      }
      this.firePropertyChange(PROPERTY_GAME, oldValue, value);
      return this;
   }

    public List<Player> getPlayers()
   {
      return this.players != null ? Collections.unmodifiableList(this.players) : Collections.emptyList();
   }

    public Card withPlayers(Player value)
   {
      if (this.players == null)
      {
         this.players = new ArrayList<>();
      }
      if (!this.players.contains(value))
      {
         this.players.add(value);
         value.withCards(this);
         this.firePropertyChange(PROPERTY_PLAYERS, null, value);
      }
      return this;
   }

    public Card withPlayers(Player... value)
   {
      for (final Player item : value)
      {
         this.withPlayers(item);
      }
      return this;
   }

    public Card withPlayers(Collection<? extends Player> value)
   {
      for (final Player item : value)
      {
         this.withPlayers(item);
      }
      return this;
   }

    public Card withoutPlayers(Player value)
   {
      if (this.players != null && this.players.remove(value))
      {
         value.withoutCards(this);
         this.firePropertyChange(PROPERTY_PLAYERS, value, null);
      }
      return this;
   }

    public Card withoutPlayers(Player... value)
   {
      for (final Player item : value)
      {
         this.withoutPlayers(item);
      }
      return this;
   }

    public Card withoutPlayers(Collection<? extends Player> value)
   {
      for (final Player item : value)
      {
         this.withoutPlayers(item);
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

    @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      result.append(' ').append(this.getColor());
      result.append(' ').append(this.getName());
      return result.substring(1);
   }

    public void removeYou()
   {
      this.setGame(null);
      this.withoutPlayers(new ArrayList<>(this.getPlayers()));
   }
}
