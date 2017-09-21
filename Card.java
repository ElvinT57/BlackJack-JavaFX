import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
/**
 * Class that represents a playing card. This class inherits from
 * the JavaFX node Image. 
 * 
 * @author Elvin Torres 
 * @version 07/27/17
 */
public class Card extends ImageView
{
    //Fields and Classes
    private int value;
    private String cardName;
    private String cardNumber;
    /**
     * Constructor #1
     * @param name Name of card
     */
    public Card(String name){
        //Calls the super constructor to retrieve the image
        super("images//"+name + ".png");
        //intializes the fields and settings
        cardName = name;
        assignValue(name);  //for the value field
        setFitWidth(108);
        setFitHeight(162);
    }

    /**
     * Assigns the value of the card by tokenizing its name
     * and checking the first token.
     */
    public void assignValue(String name){
        //tokenizes the name with the "_" charater
        String [] tokens = name.split("_");
        //retrieves the first token
        String firstToken = tokens[0];
        cardNumber = tokens[0];
        //Evaluates the firstToken
        switch(firstToken){
            case "2":
            value = 2;
            break;
            case "3":
            value = 3;
            break;
            case "4":
            value = 4;
            break;
            case "5":
            value = 5;
            break;
            case "6":
            value = 6;
            break;
            case "7":
            value = 7;
            break;
            case "8":
            value = 8;
            break;
            case "9":
            value = 9;
            break;
            case "10":
            value = 10;
            break;
            case "king":
            value = 10;
            break;
            case "queen":
            value = 10;
            break;
            case "jack":
            value = 10;
            break;
            case "ace":
            //do not give the ace card any value yet. Let the player decide with the conditions
            break;
            case "flippedCard":
            //unknown value
            break;
            default:
            AlertBox.display("Message", "Unknown Card");
        }
    }

    //-----------------ACCESSORS----------------
    public int getValue(){
        return value;
    }

    public String getName(){
        return cardName;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    /**
     * To string method
     */
    public String toString(){
        return "Card name: " + cardName +"\nValue: " + value + "\n";
    }
}