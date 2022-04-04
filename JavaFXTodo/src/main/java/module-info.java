module a8.mwisehart.javafxtodo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;


    opens a8.mwisehart.javafxtodo to javafx.fxml;
    exports a8.mwisehart.javafxtodo;
}