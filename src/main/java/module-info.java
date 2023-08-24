module example.farmacia {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens example.farmacia to javafx.fxml;
    exports example.farmacia;
}