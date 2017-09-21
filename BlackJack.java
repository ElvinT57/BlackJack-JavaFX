import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.FXML;
import java.io.IOException;
/**
 * Write a description of class BlackJack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BlackJack extends Application
{
    public void start(Stage stage) throws IOException{
        //load the FXML file.
        Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        //Build the scene graph
        Scene scene = new Scene(parent); 
        //display our window using the scene graph
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Black Jack");
        stage.setHeight(669);
        stage.setOnCloseRequest(e -> {
                int option = AlertBox.prompt("Message","Are you sure you want to exit?");
                if(option == AlertBox.YES_OPTION)
                    System.exit(0);
                else
                    e.consume();
            });
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}