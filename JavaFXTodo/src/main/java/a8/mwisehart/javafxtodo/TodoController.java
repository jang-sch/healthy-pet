package a8.mwisehart.javafxtodo;
//23:49
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TodoController {

    private String selectedTab;
    private final ObservableList<Item> items = FXCollections.observableArrayList();

    @FXML
    TextField txtItem;

    @FXML
    ListView<Item> listItems;

    @FXML
    public void initialize(){
        listItems.setItems(items);
    }

    @FXML
    private void onTabChanged(Event e) {
        Tab tab = (Tab) e.getTarget();
        if(tab.isSelected()){
            selectedTab = tab.getText();
            System.out.println(tab.getText());
            //we will get a list of items using the selected tab
            items.clear();
            APIBridge.getList(selectedTab, items);
        }
    }

    @FXML
    private void onItemClicked(MouseEvent click){
        if(click.getClickCount() == 2){
            Item selectedItem = listItems.getSelectionModel().getSelectedItem();
            items.remove(selectedItem);
            APIBridge.deleteItem(selectedTab, selectedItem);
        }
    }

    @FXML
    private void onAddItem(Event e){

        Item item = new Item(txtItem.getText());
        items.add(item);
        txtItem.setText(null);
        APIBridge.addItem(selectedTab, item);
    }

}