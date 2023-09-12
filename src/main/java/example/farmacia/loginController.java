package example.farmacia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.*;

public class loginController {

    @FXML
    private PasswordField contraIngreso;

    @FXML
    private ComboBox<String> usuarioComboBox;

    @FXML
    private TextField usuarioIngreso;

    @FXML
    void initialize() {
        usuarioComboBox.getItems().addAll("Administrador", "Cajero");
    }

    @FXML
    void inicioBoton(ActionEvent event) {
        String usuario = usuarioIngreso.getText();
        String pass = contraIngreso.getText();
        String rolSeleccionado = usuarioComboBox.getValue();

        if (validarCredenciales(usuario, pass, rolSeleccionado)) {
            getData.adminNombre = usuarioIngreso.getText();
            getData.nombreUsuario = usuarioIngreso.getText();
            cargarVista(event, rolSeleccionado.toLowerCase() + ".fxml");
        } else {
            mostrarMensajeError("Credenciales incorrectas", "El usuario o la contraseña son incorrectos.");
        }
    }
    private void mostrarMensajeError(String titulo, String contenido) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }


    private boolean validarCredenciales(String usuario, String pass, String rol) {
        final String DB_URL="jdbc:mysql://localhost/farmacia";
        final String USER = "root";
        final String PASS ="gilmar2003";
        final String QUERY= "SELECT * FROM " + rol.toUpperCase();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY + " WHERE Usuario='" + usuario + "' AND Password='" + pass + "'");
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void cargarVista(ActionEvent event, String vistaFxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(vistaFxml));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
