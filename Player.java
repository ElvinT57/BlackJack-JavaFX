import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
/**
 * Class for a Black Jack player. This class contains all the methods and fields
 * to play Black jack. 
 * 
 * @author Elvin Torres
 * @version 7/30/17
 */
public class Player
{
    //Fields and Classes
    private IntegerProperty total;  //player's total
    private ArrayList<Card> hand;   //player's hand (hand consist of Card Objects)

    /**
     * Default Constructor
     */
    public Player(){
        total = new SimpleIntegerProperty(0);
        hand = new ArrayList<>();
    }

    //-----------ACCESSORS----------
    public int getTotal(){
        return total.getValue();
    }

    /**
     * Returns the integerProperty for reference
     */
    public IntegerProperty getTotalIntegerProperty(){
        return total;
    }

    /**
     * Returns the arraylist that contains the
     * references to the card objects
     */
    public ArrayList<Card> getHand(){
        return hand;
    }

    //--------MUTATOR METHODS--------
    /**
     * Resets all the fields for this class
     */
    public void reset(){
        total = null;
        total = new SimpleIntegerProperty(0);
        hand.clear();
    }

    /**
     * Gets a card from the deck
     * @param deckIn Deck to pick a card from
     */
    public void getCard(Deck deckIn){
        //checks if the deck is empty first
        if(!deckIn.isEmpty()){
            //gets a card from the deck
            Card card = deckIn.getCard();
            if(card != null){
                //add it to the hand
                hand.add(card);
                //add the card's value to the total
                addValue(card);
            }
            //nulls the card
            card = null;
        }
        else{
            //resets the deck
            deckIn.resetDeck();
            //recurssion
            getCard(deckIn);
        }
    }

    /**
     * Calculates the value of the ace
     * according to the total
     */
    public void ace(){
        if(hand.size() == 1){
            total.setValue(total.getValue()+11);
        }
        else{
            if(total.getValue() <= 10)
                total.setValue(total.getValue()+11);   
            else
                total.setValue(total.getValue()+1);
        }
    }

    /**
     * Adds the card's value to the total of the player/dealer
     */
    public void addValue(Card card){
        //if the card is not an ace
        if(!(card.getCardNumber().equals("ace"))){
            total.setValue(total.getValue() + card.getValue());
        }
        else{   //if it is an ace card
            ace();
        }
    }
    
    public String toString(){
        return "Total: " + total.getValue() +"\n"
                + "Hand: " + hand.toString();
    }
}