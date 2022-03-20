package a6.mwisehart.fxchat.fxchat;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatViewController implements CanWriteMessage
{
    ObservableList<String> messages = FXCollections.observableArrayList();
    ObservableList<String> people = FXCollections.observableArrayList();
    private Client client;




    @FXML
    TextField txtMessage;

    @FXML
    ListView<String> listMessages, listPeople;

    @FXML
    public void initialize()
    {
        listMessages.setItems(messages);
        listPeople.setItems(people);
    }

    public void setClient(Client client)
    {
        this.client = client;
        client.listenForMessages(this);
    }

    @FXML
    private void onSendClicked()
    {
        client.sendMessage(client.getUserName() + ": " + txtMessage.getText());
        txtMessage.setText("");
    }

    @Override
    public void writeMessage(String message)
    {
        switch(message.charAt(0))
        {
            case '+' -> Platform.runLater(() -> people.add(message.substring(1)));
            case '-' -> Platform.runLater(() -> people.remove(message.substring(1)));
            default -> Platform.runLater(() -> messages.add(message));

        }
    }
}
