package de.uniks.pmws2223.uno;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Random;

import static org.junit.Assert.assertEquals;


public class GameTest extends ApplicationTest {
    private App app;
    private Stage stage;
    private Random random = new Random(5604704);


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        app = new App(random);
        app.start(stage);
    }

    @Test
    public void play3Rounds() {

        assertEquals("Setup", stage.getTitle());
        write("Alice");
        clickOn("#button1v3");
        assertEquals("Ingame", stage.getTitle());

        final Button yellowButton = (Button) lookup("#yellowButton").query();

        final HBox playerHand = (HBox) lookup("#cardBox").query();
        //check if there are 7 cards in the hand
        assertEquals(7, playerHand.getChildren().size());
        final AnchorPane botBox1 = (AnchorPane) lookup("#botBox1").query();
        //check if there are 7 cards in the hand
        assertEquals(7, botBox1.getChildren().size());
        final AnchorPane botBox2 = (AnchorPane) lookup("#botBox2").query();
        //check if there are 7 cards in the hand
        assertEquals(7, botBox2.getChildren().size());
        final AnchorPane botBox3 = (AnchorPane) lookup("#botBox3").query();
        //check if there are 7 cards in the hand
        assertEquals(7, botBox3.getChildren().size());
        final VBox TopCardBox = (VBox) lookup("#topCard").query();
        final Label currantPlayerLabel = (Label) lookup("#currantPlayer").query();
        assertEquals("Alice's turn", currantPlayerLabel.getText());


        //Runde 1
        //Topcard is 8 yellow
        //Alice plays 1 yellow
        clickOn(playerHand.getChildren().get(3));
        assertEquals(6, playerHand.getChildren().size());//check if there are 6 cards in the hand
        assertEquals("Bot1's turn", currantPlayerLabel.getText());//check if it is Bot1´s turn
        sleep(2000);
        //Bot 1 plays 6 yellow
        assertEquals(6, botBox1.getChildren().size()); //check if there are 6 cards in the hand
        //Bot 2 plays 1 green
        assertEquals("Bot2's turn", currantPlayerLabel.getText());//check if it is Bot2´s turn
        sleep(2000);
        assertEquals(6, botBox2.getChildren().size());//check if there are 6 cards in the hand
        //Bot 3 plays wild and set´s color red
        assertEquals("Bot3's turn", currantPlayerLabel.getText());//check if it is Bot3´s turn
        sleep(2000);
        assertEquals(6, botBox3.getChildren().size());//check if there are 6 cards in the hand
        // it is Alice´s turn
        assertEquals("Alice's turn", currantPlayerLabel.getText());


        //Runde 2
        //Topcard is wild red
        //Alice plays 4 red
        clickOn(playerHand.getChildren().get(3));
        assertEquals(5, playerHand.getChildren().size());//check if there are 5 cards in the hand
        assertEquals("Bot1's turn", currantPlayerLabel.getText());//check if it is Bot1´s turn
        sleep(2000);
        //Bot 1 plays 9 red
        assertEquals(5, botBox1.getChildren().size());//check if there are 5 cards in the hand
        //Bot 2 plays 9 red
        assertEquals("Bot2's turn", currantPlayerLabel.getText());//check if it is Bot2´s turn
        sleep(2000);
        assertEquals(5, botBox2.getChildren().size());//check if there are 5 cards in the hand
        //Bot 3 plays 8 red
        assertEquals("Bot3's turn", currantPlayerLabel.getText());//check if it is Bot3´s turn
        sleep(2000);
        assertEquals(5, botBox3.getChildren().size());
        // it is Alice´s turn
        assertEquals("Alice's turn", currantPlayerLabel.getText());//check if it is Alice´s turn


        //Runde 3
        //Topcard is 8 red
        //Alice set´s color to yellow
        clickOn(yellowButton);
        //Alice plays wild card
        clickOn(playerHand.getChildren().get(2));
        assertEquals(4, playerHand.getChildren().size());//check if there are 4 cards in the hand
        assertEquals("Bot1's turn", currantPlayerLabel.getText());//check if it is Bot1´s turn
        sleep(2000);
        //Bot 1 plays 5 yellow
        assertEquals(4, botBox1.getChildren().size());//check if there are 4 cards in the hand
        //Bot 2 plays 3 yellow
        assertEquals("Bot2's turn", currantPlayerLabel.getText());//check if it is Bot2´s turn
        sleep(2000);
        assertEquals(4, botBox2.getChildren().size());//check if there are 4 cards in the hand
        //Bot 3 plays 3 blue
        assertEquals("Bot3's turn", currantPlayerLabel.getText());//check if it is Bot3´s turn
        sleep(2000);
        assertEquals(4, botBox3.getChildren().size());//check if there are 4 cards in the hand
        //Topcard is 3 blue
        // it is Alice´s turn
        assertEquals("Alice's turn", currantPlayerLabel.getText());//check if it is Alice´s turn

    }


}
