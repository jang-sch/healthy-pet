package a3.mwisehart.contactsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Contacts extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        //scenes are built in fxml and we load them from hello-view.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(Contacts.class.getResource("list-view.fxml"));
        //making a new scene with the fxmlloader and it has width and height
        Scene scene = new Scene(fxmlLoader.load());
        //sets window
        ListViewController controller = fxmlLoader.getController();
        stage.setTitle("Contacts");
        //using the stage we set the scene from xlm
        stage.setScene(scene);
        stage.setOnHidden(e -> controller.shutdown());
        //displays the scene
        stage.show();
    }

    //main file that calls launch.
    //gets app ready to run and calls public void start function above
    //stage is the area we put scenes on
    public static void main(String[] args)
    {
        launch();
    }
}