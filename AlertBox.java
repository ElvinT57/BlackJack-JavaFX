import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.*;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Pos;
/**
 * static class used to display a message on the screen using
 * the display static method.
 * 
 * @author Elvin Torres
 * @version 1.0
 */
public class AlertBox
{
    //Message types
    public static final int PLAIN_MESSAGE = 0;
    public static final int QUESTION_MESSAGE = 1;
    public static final int INFORMATION_MESSAGE = 2;
    public static final int ERROR_MESSAGE = 3;
    public static final int WARNING_MESSAGE = 4;
    //Answer types
    public static final int NO_OPTION = 0;
    public static final int YES_OPTION = 1;
    //for the displayQuestion method
    private static int option = -1;
    /**
     * Displays a message in a new window
     * @param title Title of the window
     * @param message Message to display
     */
    public static void display(String title, String message){
        Stage window = new Stage();
        //prevents user events in other windows/stages
        window.initModality(Modality.APPLICATION_MODAL);
        //sets the size of the window/stage
        window.setWidth(250);
        window.setHeight(100);
        window.setResizable(false);
        //sets the title of the window
        window.setTitle(title);
        //creates and intializes components
        Label label = new Label(message);
        Button exit = new Button("Okay");
        label.setStyle("-fx-font-family: Century;");
        label.setStyle("-fx-font-size: 12px;");
        //----------Layout---------
        VBox box = new VBox();
        box.getChildren().addAll(label, exit);
        //sets the alignment and other settings for the layout
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        //sets the action for the button
        exit.setOnAction(e -> window.close());
        //creates the scene of the window
        Scene scene = new Scene(box);
        //Sets the scene of the stage
        window.setScene(scene);
        //waits for the user to handle the window
        window.showAndWait(); 
    }
    
    /**
     * Displays a question in new window
     * @param title Title of the window
     * @param question Question to prompt
     * @return int, yes = 1, no = 0, no answer = -1;
     */
    public static int prompt(String title, String question){
        //resets the static value for this method
        option = -1;
        /////////////////////////////////////////////////////
        Stage window = new Stage();
        //prevents user events in other windows/stages
        window.initModality(Modality.APPLICATION_MODAL);
        //sets the size of the window/stage
        window.setWidth(250);
        window.setHeight(100);
        window.setResizable(false);
        //sets the title of the window
        window.setTitle(title);
        //creates and intializes components
        Label label = new Label(question);
        label.setStyle("-fx-font-family: Century;");
        label.setStyle("-fx-font-size: 12px;");
        Button yes = new Button("Yes");
        Button no = new Button("No");
        FlowPane fp = new FlowPane(yes, no);
        fp.setAlignment(Pos.CENTER);
        fp.setHgap(8);
        //----------Layout---------
        VBox box = new VBox();
        box.getChildren().addAll(label, fp);
        //sets the alignment and other settings for the layout
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);   
        //sets the action for the button
        yes.setOnAction(e -> {
            option = YES_OPTION;
            window.close();
        });
        no.setOnAction(e -> {
            option = NO_OPTION;
            window.close();
        });
        //creates the scene of the window
        Scene scene = new Scene(box);
        //Sets the scene of the stage
        window.setScene(scene);
        //waits for the user to handle the window
        window.showAndWait(); 
        //return statement
        return option;
    }
}