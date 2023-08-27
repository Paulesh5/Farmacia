package example.farmacia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;

import java.util.Optional;
import java.util.ResourceBundle;

public class administradorController {
    @FXML
    private TableColumn<datosUsuarios, String> apellidoUsuarioColumnaTabla;

    @FXML
    private TextField apellidoUsuarioIngreso;

    @FXML
    private TextField buscarUsuarioIngreso;

    @FXML
    private TableColumn<datosUsuarios, String> cedulaUsuarioColumnaTabla;

    @FXML
    private TextField cedulaUsuarioIngreso;

    @FXML
    private TableColumn<?, ?> codigoProductoColumnaTabla;

    @FXML
    private TextField codigoProductoIngreso;

    @FXML
    private TableColumn<datosUsuarios, String> correoUsuarioColumnaTabla;

    @FXML
    private TextField correoUsuarioIngreso;

    @FXML
    private TableColumn<datosUsuarios, String> edadUsuarioColumnaTabla;

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
    private TableColumn<datosUsuarios, String> nombreUsuarioColumnaTabla;

    @FXML
    private TextField nombreUsuarioIngreso;

    @FXML
    private TextField passwordUsuarioIngreso;

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
    private TableColumn<datosUsuarios, String> usuarioColumnaTabla;

    @FXML
    private AnchorPane usuarioForm;

    @FXML
    private TextField usuarioIngreso;

    @FXML
    private TableView<datosUsuarios> usuarioTabla;

    @FXML
    private Button usuariosBoton;

    @FXML
    private Button ventasBoton;

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    public ObservableList<datosUsuarios> addListaDatosUsuarios(){

        ObservableList<datosUsuarios> listaDatos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CAJERO";

        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            datosUsuarios datoUsuario;

            while (result.next()){
                datoUsuario = new datosUsuarios(result.getString("Usuario")
                        , result.getString("Nombre")
                        , result.getString("Apellido")
                        , result.getString("Cedula")
                        , result.getInt("Edad")
                        , result.getString("Correo")
                        , result.getString("Password"));
                listaDatos.add(datoUsuario);
            }
        }catch (Exception e){e.printStackTrace();}
        return listaDatos;
    }

    private ObservableList<datosUsuarios> addListaDatosUsuarios;
    public void addListaDatosUsuariosMostrar(){
        addListaDatosUsuarios = addListaDatosUsuarios();

        usuarioColumnaTabla.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        nombreUsuarioColumnaTabla.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoUsuarioColumnaTabla.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        cedulaUsuarioColumnaTabla.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        edadUsuarioColumnaTabla.setCellValueFactory(new PropertyValueFactory<>("edad"));
        correoUsuarioColumnaTabla.setCellValueFactory(new PropertyValueFactory<>("correo"));

        usuarioTabla.setItems(addListaDatosUsuarios);
    }

    public void addUsuariosSelect(){
        datosUsuarios datoUsuario = usuarioTabla.getSelectionModel().getSelectedItem();
        int num = usuarioTabla.getSelectionModel().getSelectedIndex();

        if((num - 1) < -1){
            return;
        }

        usuarioIngreso.setText(datoUsuario.getUsuario());
        nombreUsuarioIngreso.setText(datoUsuario.getNombre());
        apellidoUsuarioIngreso.setText(datoUsuario.getApellido());
        cedulaUsuarioIngreso.setText(datoUsuario.getCedula());
        edadUsuarioIngreso.setText(String.valueOf(datoUsuario.getEdad()));
        correoUsuarioIngreso.setText(datoUsuario.getCorreo());
        passwordUsuarioIngreso.setText("**********");
    }

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
        String sql = "INSERT INTO CAJERO "
                + "(Usuario,Nombre,Apellido,Cedula,Edad,Correo,Password) "
                + "VALUES(?,?,?,?,?,?,?)";
        connect = database.connectDb();

        try{
            Alert alert;
            if (usuarioIngreso.getText().isEmpty()
            || nombreUsuarioIngreso.getText().isEmpty()
            || apellidoUsuarioIngreso.getText().isEmpty()
            || cedulaUsuarioIngreso.getText().isEmpty()
            || edadUsuarioIngreso.getText().isEmpty()
            || correoUsuarioIngreso.getText().isEmpty()
            || passwordUsuarioIngreso.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mensaje Error");
                alert.setHeaderText(null);
                alert.setContentText("Por favor llenar todos los campos");
                alert.showAndWait();
            }else{

                String check = "SELECT Usuario FROM CAJERO WHERE Usuario = '"
                        +usuarioIngreso.getText()+"'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Mensaje Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Usuario: " + usuarioIngreso.getText() + " ya existe!");
                    alert.showAndWait();
                }else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, usuarioIngreso.getText());
                    prepare.setString(2, nombreUsuarioIngreso.getText());
                    prepare.setString(3, apellidoUsuarioIngreso.getText());
                    prepare.setString(4, cedulaUsuarioIngreso.getText());
                    prepare.setString(5, edadUsuarioIngreso.getText());
                    prepare.setString(6, correoUsuarioIngreso.getText());
                    prepare.setString(7, passwordUsuarioIngreso.getText());
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensaje informativo");
                    alert.setHeaderText(null);
                    alert.setContentText("Creado exitosamente!");
                    alert.showAndWait();
                    addListaDatosUsuariosMostrar();
                    limpiarFormulario();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void limpiarFormulario(){
        usuarioIngreso.setText("");
        nombreUsuarioIngreso.setText("");
        apellidoUsuarioIngreso.setText("");
        cedulaUsuarioIngreso.setText("");
        edadUsuarioIngreso.setText("");
        correoUsuarioIngreso.setText("");
        passwordUsuarioIngreso.setText("");
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
        usuarioIngreso.setText("");
        nombreUsuarioIngreso.setText("");
        apellidoUsuarioIngreso.setText("");
        cedulaUsuarioIngreso.setText("");
        edadUsuarioIngreso.setText("");
        correoUsuarioIngreso.setText("");
        passwordUsuarioIngreso.setText("");
    }

    public void iniciar(URL location, ResourceBundle resources){
        addListaDatosUsuariosMostrar();
    }
}//2:04:46
