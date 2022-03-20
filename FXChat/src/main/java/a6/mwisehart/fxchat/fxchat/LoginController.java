package a6.mwisehart.fxchat.fxchat;
//ts 1:00
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class LoginController
{
    @FXML
    TextField txtUserName;

    @FXML
    VBox vboxMessage;

    @FXML
    private void initialize()
    {
        vboxMessage.setVisible(false);
        vboxMessage.setManaged(false);
    }

    @FXML
    private void onLoginClicked()
    {
        if(!validateForm(false)) return;

        try
        {
            Socket socket = new Socket("odin.cs.csub.edu", 3390);
            Client client = new Client(socket, txtUserName.getText());
            client.sendMessage(txtUserName.getText());
            openChatView(client);


        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void openChatView(Client client) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(FXChatApplication.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600,400);
        ChatViewController chatViewController = fxmlLoader.getController();
        chatViewController.setClient(client);
        Stage stage = (Stage) txtUserName.getScene().getWindow();
        stage.setOnHidden(event -> {client.closeEverything();});
        stage.setScene(scene);
    }

    private boolean validateForm(boolean b)
    {
        boolean nameIsValid = txtUserName.getText().matches("^\\w{2,9}[a-zA-Z0-9]$");
        if(nameIsValid)
        {
            return true;
        }
        else
        {
            vboxMessage.setManaged(true);
            vboxMessage.setVisible(true);
            txtUserName.setStyle("-fx-border-color:#FF0000");
            return false;
        }
    }


}