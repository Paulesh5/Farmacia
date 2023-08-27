module example.farmacia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens example.farmacia to javafx.fxml;
    exports example.farmacia;
}