import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.Random;
import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

/**
 * Main Controller for the black jack game
 * 
 * @author Elvin Torres, Michael Hackett
 * @version 08/01/17
 */
public class BJMainController
{
    /*******************************************
     * INJECTED COMPONENTS FROM JAVAFX SCENE BUILDER 2.0
     */
    @FXML
    private ResourceBundle resources;

    @FXML
    private Label cardsLeftLabel;

    @FXML
    private URL location;

    @FXML
    private Button playBtn;

    @FXML
    private FlowPane playersRow;

    @FXML
    private Label dealersScore;

    @FXML
    private Label displayBox;

    @FXML
    private FlowPane dealersRow;

    @FXML
    private Label playersScore;

    @FXML
    private Label dealersTotal;

    @FXML
    private Button hitBtn;

    @FXML
    private Label playersTotal;

    @FXML
    private Button stayBtn;
    
    /**********************************
     *Fields and Classes
     */
    private Player player;
    private Player dealer;
    private Deck deck;
    //game fields
    private boolean disabled;   //boolean that keeps track of the player's ability to use the control
    private int pScore; //The player's winning score
    private int dScore; ///the dealer's winning score
    private IntegerProperty pTotal; //the player's total integer property(a change listener will be added to this property)
    private IntegerProperty dTotal; //the dealer's total integer property(a change listener will be added to this property)
    private boolean aPlayerBusted;  //a boolean that keeps track of a players busting
    private Card flippedCard;   //a card object that will be intialized with the flippedCard value(for the dealer)

    @FXML
    void initialize() {
        assert playersRow != null : "fx:id=\"playersRow\" was not injected: check your FXML file 'BJMain.fxml'.";
        assert dealersScore != null : "fx:id=\"dealersScore\" was not injected: check your FXML file 'BJMain.fxml'.";
        assert displayBox != null : "fx:id=\"displayBox\" was not injected: check your FXML file 'BJMain.fxml'.";
        assert dealersRow != null : "fx:id=\"dealersRow\" was not injected: check your FXML file 'BJMain.fxml'.";
        assert playersScore != null : "fx:id=\"playersScore\" was not injected: check your FXML file 'BJMain.fxml'.";
        assert dealersTotal != null : "fx:id=\"dealersTotal\" was not injected: check your FXML file 'BJMain.fxml'.";
        assert hitBtn != null : "fx:id=\"hitBtn\" was not injected: check your FXML file 'BJMain.fxml'.";
        assert playersTotal != null : "fx:id=\"playersTotal\" was not injected: check your FXML file 'BJMain.fxml'.";
        assert stayBtn != null : "fx:id=\"stayBtn\" was not injected: check your FXML file 'BJMain.fxml'.";
        //initalizes the game settings and fields
        player = new Player();
        dealer = new Player();
        deck = new Deck();
        disabled = true;
        pScore = 0;
        dScore = 0;
        aPlayerBusted = false;
        pTotal = new SimpleIntegerProperty(0);
        dTotal = new SimpleIntegerProperty(0);
        flippedCard = new Card("flippedCard");
        //PROPERTY CHANGE LISTENERS
        pTotal.addListener( (OValue, oldVal, newVal) ->{
                this.checkIfBust(); //when a player's total has change, run the checkIfBust Method to check if they busted
            });

        dTotal.addListener( (OValue, oldVal, newVal) ->{
                this.checkIfBust();
            });
    }

    //==================== GAME MECHANICS =========================

    /**
     * Starts the game
     */
    public void startGame(){
        //hides the title display
        displayBox.setVisible(false);
        //resets the game just in case
        resetGame();
        //sets up the game
        dealFirstCards();
        //vanishes the playBtn from the display box
        playBtn.setDisable(true);
        playBtn.setVisible(false);
        //toggles the control buttons to enable
        toggleControlButtons();
        //Garbage collection
        System.gc();
    }

    /**
     * Gives two cards to both of the players
     */
    public void dealFirstCards(){
        /*************************
         * for the player
         */
        hit();
        hit();
        /*************************
         * for the dealer
         * 
         * Uses the flipped card to hide the second card
         */
        dealer.getCard(deck);
        dealer.getCard(deck);
        //Update the dealer's row with the random card and the flippedCard
        dealersRow.getChildren().addAll(dealer.getHand().get(0), flippedCard);
        dealersRow.setMargin(flippedCard, new Insets(0,0,0,-50));   //sets margin to the flippedCard only
        updateCardsLeft();
        //set the dealer's total unknown
        dealersTotal.setText("?");
    }

    /**
     * Reveals the dealer's card and updates the total
     */
    private void revealDealersCard(){
        //updates the row using the dealer's hand
        updateRow(dealersRow, dealer.getHand());
        updateTotal(dealersTotal, dealer.getTotal(), dTotal);
        //checks for a winner just in case
        if (dealer.getTotal()>=17) {
            checkWinner();
        }
    }

    /**
     * Function for the player to hit
     */
    public void hit(){
        //get a card using the ceck object
        player.getCard(deck);
        //update the total label using the player's integerProperty and the instance's pTotal field
        updateTotal(playersTotal, player.getTotal(), pTotal);
        updateRow(playersRow, player.getHand());
        updateCardsLeft();  //update the cards left label
        //Garbage Collection
        System.gc();
    }

    /**
     * Function for the player to stay
     */
    public void stay(){
        toggleControlButtons();
        dealersTurn();
    }

    /**
     * Lets the dealer play
     * @author Michael Hackett, Elvin Torres
     */
    private void dealersTurn(){
        //reveal the dealer's second card
        revealDealersCard();
        //if the the dealer has not reached 17
        if(!(dealer.getTotal() >= 17)){
            /**********************
             * Task that needs to run on a separate thread from the JavaFX Thread.
             */
            Task<Integer> task = new Task<Integer>() {
                    @Override protected Integer call() throws Exception {
                        while(dealer.getTotal()<17) {
                            dealer.getCard(deck);
                            /***************
                             * Pauses this second thread.
                             */
                            try {
                                Thread.sleep(700); // 7/10 of a second
                            } catch (InterruptedException interrupted) {
                                //i dont care
                            }

                            /***********************
                             * Stuff that needs to happen back on the JavaFX Thread. 
                             * The method calls (updateRow, updateTotal, and checkWinner) can't be done from this second thread.
                             * (The object that has those methods is back on the JavaFX thread.)
                             */
                            Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateRow(dealersRow, dealer.getHand());
                                        updateTotal(dealersTotal, dealer.getTotal(), dTotal);
                                        updateCardsLeft();
                                        if (dealer.getTotal()>=17) {
                                            checkWinner();
                                        }
                                    }
                                });
                        }
                        this.cancelled(); //Stops the thread
                        return null;
                    }
                };
            /*******************
             * Start the other thread.
             */
            Thread t = new Thread(task);
            t.start();
        }
    }

    /**
     * Checks the winner of the game
     */
    public void checkWinner(){
        if(!aPlayerBusted){
            if(dealer.getTotal() == player.getTotal()){
                //when players tie!
                displayMessage("Tie!");
            }
            else if(dealer.getTotal() < player.getTotal()){
                displayMessage("Player wins!");
                pScore++;   //updates score
                playersScore.setText(Integer.toString(pScore));
            }
            else{
                displayMessage("Dealer wins!");
                dScore++;   //updates score for dealer
                dealersScore.setText(Integer.toString(dScore));
            }
            displayReplayButton();
        }
        else{
            toggleControlButtons();
        }
    }

    /**
     * Checks if any of the players bust
     */
    public void checkIfBust(){
        if(player.getTotal() > 21){
            displayMessage("Player Bust!");
            dScore++;
            dealersScore.setText(Integer.toString(dScore));
            aPlayerBusted = true;
            displayReplayButton();
            toggleControlButtons();
        }
        else if(dealer.getTotal() > 21){
            displayMessage("Dealer Bust!");
            pScore++;   //updates score
            playersScore.setText(Integer.toString(pScore));
            aPlayerBusted = true;
            displayReplayButton();
            toggleControlButtons();
        }
    }

    /**
     * Resets the game, this includes the players' score
     * , their hands, and boolean conditions
     */
    private void resetGame(){
        //resets the players
        player.reset();
        dealer.reset();
        //resets the text back to 0
        playersTotal.setText("0");
        dealersTotal.setText("0");
        //clears out the flowpane rows
        dealersRow.getChildren().clear();
        playersRow.getChildren().clear();
        //reset the player busted boolean
        aPlayerBusted = false;
    }

    //================== UI FUNCTIONS ======================
    /**
     * Updates the total of the corresponding player
     */
    private void updateTotal(Label label, int total, IntegerProperty IP){
        IP.set(total);
        label.setText(Integer.toString(IP.getValue()));
    }
    
    /**
     * Displays a replay button in the center display
     */
    private void displayReplayButton(){
        playBtn.setText("Play Again");
        playBtn.setDisable(false);
        playBtn.setVisible(true);
        //Garbage collection
        System.gc();
    }

    /**
     * Updates the Row that is passed in the argument using 
     * the corresponding hand
     */
    private void updateRow(FlowPane row, ArrayList<Card> hand){
        //clears out all the children from the flowpane
        row.getChildren().clear();
        //for each card from the hand, add it to the row
        for(int i = 0; i<hand.size(); i++){
            //add card to the row
            row.getChildren().add(hand.get(i));
            //add -50 left margin to the every card after the first card
            if(i > 0)
                row.setMargin(hand.get(i), new Insets(0,0,0,-50));
        }
    }

    /**
     * Toggles the play buttons
     */
    private void toggleControlButtons(){
        //inverts the boolean
        disabled = !disabled;
        //uses the boolean to enables or disables the buttons
        hitBtn.setDisable(disabled);
        stayBtn.setDisable(disabled);
    }

    /**
     * Displays an in game message
     */
    private void displayMessage(String message){
        //sets the text of the label
        displayBox.setText(message);
        //sets the visibility of the the display box
        displayBox.setVisible(true);
    }

    /**
     * Updates the total cards left label
     */
    private void updateCardsLeft(){
        //sets the text of the cardsLeftLabel using the deck object
        cardsLeftLabel.setText(Integer.toString(deck.getTotalCardsLeft()));
    }
}