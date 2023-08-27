package example.farmacia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Optional;

public class administradorController {
    @FXML
    private TableColumn<?, ?> apellidoUsuarioColumnaTabla;

    @FXML
    private TextField apellidoUsuarioIngreso;

    @FXML
    private TextField buscarUsuarioIngreso;

    @FXML
    private TableColumn<?, ?> cedulaUsuarioColumnaTabla;

    @FXML
    private TextField cedulaUsuarioIngreso;

    @FXML
    private TableColumn<?, ?> codigoProductoColumnaTabla;

    @FXML
    private TextField codigoProductoIngreso;

    @FXML
    private TableColumn<?, ?> correoUsuarioColumnaTabla;

    @FXML
    private TextField correoUsuarioIngreso;

    @FXML
    private TableColumn<?, ?> edadUsuarioColumnaTabla;

    @FXML
    private TextField edadUsuarioIngreso;

    @FXML
    private Button homeBoton;

    @FXML
    private AnchorPane homeForm;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private Label nombreAdmin;

    @FXML
    private TableColumn<?, ?> nombreProductoColumnaTabla;

    @FXML
    private TextField nombreProductoIngreso;

    @FXML
    private TableColumn<?, ?> nombreUsuarioColumnaTabla;

    @FXML
    private TextField nombreUsuarioIngreso;

    @FXML
    private Button productosBoton;

    @FXML
    private AnchorPane productosForm;

    @FXML
    private TableView<?> productosTabla;

    @FXML
    private TableColumn<?, ?> stockProductoColumnaTabla;

    @FXML
    private TextField stockProductoIngreso;

    @FXML
    private AnchorPane switchForm;

    @FXML
    private TableColumn<?, ?> usuarioColumnaTabla;

    @FXML
    private AnchorPane usuarioForm;

    @FXML
    private TextField usuarioIngreso;

    @FXML
    private TableView<?> usuarioTabla;

    @FXML
    private Button usuariosBoton;

    @FXML
    private Button ventasBoton;

    public void displayNombreAdmin(){
        nombreAdmin.setText(getData.adminNombre);
    }
    public void switchForm(ActionEvent event){
        if (event.getSource() == homeBoton){
            homeForm.setVisible(true);
            usuarioForm.setVisible(false);
            productosForm.setVisible(false);
        }else if (event.getSource() == usuariosBoton){
            homeForm.setVisible(false);
            usuarioForm.setVisible(true);
            productosForm.setVisible(false);
        }else if (event.getSource() == productosBoton){
            homeForm.setVisible(false);
            usuarioForm.setVisible(false);
            productosForm.setVisible(true);
        }
    }
    @FXML
    void actualizarProductoBoton(ActionEvent event) {

    }

    @FXML
    void actualizarUsuarioBoton(ActionEvent event) {

    }

    @FXML
    void buscarProductoBoton(ActionEvent event) {

    }

    @FXML
    void buscarUsuarioBoton(ActionEvent event) {

    }

    @FXML
    void cerrarSesionBoton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensaje de confirmación");
        alert.setHeaderText(null);
        alert.setContentText("Seguro de querer cerrar sesión?");
        Optional<ButtonType> option = alert.showAndWait();
        try{
            if (option.get().equals(ButtonType.OK)){

                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }catch(Exception e){e.printStackTrace();}
    }

    @FXML
    void crearUsuarioBoton(ActionEvent event) {

    }

    @FXML
    void eliminarProductoBoton(ActionEvent event) {

    }

    @FXML
    void eliminarUsuarioBoton(ActionEvent event) {

    }

    @FXML
    void limpiarProductoBoton(ActionEvent event) {

    }

    @FXML
    void limpiarUsuarioBoton(ActionEvent event) {

    }
}
