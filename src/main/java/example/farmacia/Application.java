package example.farmacia;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Application extends javafx.application.Application {

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("menu-principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("OWL-PHARMA");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}