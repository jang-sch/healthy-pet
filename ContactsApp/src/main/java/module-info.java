module a3.mwisehart.contactsapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens a3.mwisehart.contactsapp to javafx.fxml;
    exports a3.mwisehart.contactsapp;
}