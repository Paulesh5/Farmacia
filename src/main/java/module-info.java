module example.farmacia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;
    requires java.sql.rowset;


    opens example.farmacia to javafx.fxml;
    exports example.farmacia;
}