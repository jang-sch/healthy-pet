module a6.mwisehart.fxchat.fxchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens a6.mwisehart.fxchat.fxchat to javafx.fxml;
    exports a6.mwisehart.fxchat.fxchat;
}