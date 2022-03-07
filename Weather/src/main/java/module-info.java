module a4.mwisehart.weather {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.net.http;


    opens a4.mwisehart.weather to javafx.fxml;
    exports a4.mwisehart.weather;
}