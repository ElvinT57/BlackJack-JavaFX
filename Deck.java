import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.lang.StringBuilder;
/**
 * This is class of a playing card deck. The fields consist of a random number
 * generator that randomly assigns a card. Integer that represent the total
 * cards left in the deck. List of cards that have already been drawn.
 * 
 * @author Elvin Torres 
 * @version 07/30/17
 */
public class Deck
{
    /***********************************
     *Fields and Classes
     */
    private Random rnd;
    private int totalCardsLeft;
    private Set<String> cardsDrawn;

    /**
     * Default constructor
     */
    public Deck(){
        rnd = new Random();
        totalCardsLeft = 52;
        cardsDrawn = new HashSet<>();
    }

    /**
     * Returns a random card object
     */
    public Card getCard(){
        if(!isEmpty()){
            int randomNum = 0;
            StringBuilder cardName = new StringBuilder("");    //string that holds the name of the card
            boolean done = false; //boolean that checks if the card has been drawn
            while(!done){
                //Gets a random number for the first prefix
                randomNum = rnd.nextInt(13)+1;
                //resets the cardName just in case of iteration
                cardName = new StringBuilder(0);
                //First Prefix for the card name
                if(randomNum == 13)
                    cardName.append("jack");
                else if(randomNum == 12)
                    cardName.append("queen");
                else if(randomNum == 11)
                    cardName.append("king");
                else if(randomNum == 1)
                    cardName.append("ace");
                else
                    cardName.append(Integer.toString(randomNum)); //parses the randNum to a string (only cards wtih numbers)
                //Appends underbound to the cardName string for the format
                cardName.append("_");

                //SECONDARY PREFIX
                randomNum = rnd.nextInt(4)+1;
                if(randomNum == 1)
                    cardName.append("of_diamonds");
                else if(randomNum == 2)
                    cardName.append("of_spades");
                else if(randomNum == 3)
                    cardName.append("of_hearts");
                else
                    cardName.append("of_clubs");

                if(!cardsDrawn.contains(cardName.toString())){
                    done = true;    //if the cardsDrawn does not contain the card then the break out of the loop and finish
                }
            }
            //adds the card to the cardsDrawn Array List
            cardsDrawn.add(cardName.toString());
            //remove card from the total amount of cards left
            totalCardsLeft--;
            //Return the randomly selected card
            return new Card(cardName.toString());
        }
        else{
            //return nothing if it is empty
            //AlertBox.display("Message", "Deck is empty.");
            return null;
        }
    }

    public boolean isEmpty(){
        if(totalCardsLeft == 0)
            return true;
        else
            return false;
    }

    /**
     * Returns the total amount of cards left in the deck
     */
    public int getTotalCardsLeft(){
        return totalCardsLeft;
    }

    /**
     * Resets the deck's total cards left.
     * This method is to be used when there is no more
     * cards left in the deck
     */
    public void resetDeck(){
        totalCardsLeft = 52;
        cardsDrawn.clear(); //clears the arraylist that contains all the cards that have drawn (retrieves cards)
    }
}