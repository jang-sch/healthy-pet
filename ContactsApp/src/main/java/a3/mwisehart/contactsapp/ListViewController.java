package a3.mwisehart.contactsapp;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ListViewController
{
    ObservableList<Contact> contacts = FXCollections.observableArrayList();
    @FXML
    ListView<Contact> contactsList;
    @FXML
    GridPane newContact;
    @FXML
    TextField txtFirstName, txtLastName, txtPhone;

    @FXML
    MenuItem btnThemeDefault, btnThemeBlue, btnThemeUnit02;

    @FXML
    VBox vboxMain;



    ContactComparator comparator = new ContactComparator();


    @FXML
    //intialize method runs after scene is built
    public void initialize()
    {
        contactsList.setItems(contacts);
        setNewContactVis(false);
        loadListFromJson();
        contacts.sort(comparator);
    }

    private void loadListFromJson()
    {
        try (Scanner scanner = new Scanner(new File("contacts.json")))
        {
            while(scanner.hasNextLine())
            {
                String contactString = scanner.nextLine();
                Contact tmp = new Contact(new JSONObject(contactString));
                contacts.add(tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } ;
    }
    
    @FXML
    public void shutdown()
    {
        System.out.println("Shutting Down");
        try(FileWriter file = new FileWriter("contacts.json"))
        {
            for(int i = 0; i < contacts.size(); i++)
            {
                file.write(contacts.get(i).getJSONString());
                if(i != contacts.size() -1)
                {
                    file.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    @FXML
    protected void onNewContact()
    {
        setNewContactVis(true);

    }

    @FXML
    protected void onSaveContact()
    {
        Contact tmp = new Contact(txtFirstName.getText(), txtLastName.getText(), txtPhone.getText());
        contacts.add(tmp);
        setNewContactVis(false);
        txtFirstName.setText(null);
        txtLastName.setText(null);
        txtPhone.setText(null);
        contacts.sort(comparator);

    }

    @FXML
    protected void onEditContact()
    {
        Contact selectedContact = contactsList.getSelectionModel().getSelectedItem();
        if(selectedContact != null)
        {
            onNewContact();
            txtFirstName.setText(selectedContact.getFirstName());
            txtLastName.setText(selectedContact.getLastName());
            txtPhone.setText(selectedContact.getPhone());
            onDeleteContact();
        }
    }

    @FXML
    protected void onThemeChange(final ActionEvent event)
    {
        String defaultCSS = Objects.requireNonNull(getClass().getResource("Default.css")).toString();
        String blueCSS = Objects.requireNonNull(getClass().getResource("Blue.css")).toString();
        String unit02CSS = Objects.requireNonNull(getClass().getResource("Unit02.css")).toString();
        vboxMain.getScene().getStylesheets().removeAll(defaultCSS, blueCSS, unit02CSS);

        Object source = event.getSource();
        if (btnThemeDefault.equals(source))
        {
            vboxMain.getScene().getStylesheets().add(defaultCSS);
        } else if (btnThemeBlue.equals(source))
        {
            vboxMain.getScene().getStylesheets().add(blueCSS);
        } else if (btnThemeUnit02.equals(source))
        {
            vboxMain.getScene().getStylesheets().add(unit02CSS);
        }

    }

    @FXML
    protected void onDeleteContact()
    {
        Contact selectedContact = contactsList.getSelectionModel().getSelectedItem();
        if(selectedContact != null)
        {
            contacts.remove(selectedContact);
        }
    }

    private void setNewContactVis(Boolean vis)
    {
        newContact.setVisible(vis);
        newContact.setManaged(vis);
    }

}
