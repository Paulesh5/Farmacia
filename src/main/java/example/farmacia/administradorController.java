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
    private TableColumn<datosProductos, String> codigoProductoColumnaTabla;

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
    private TableColumn<datosProductos, String> nombreProductoColumnaTabla;

    @FXML
    private TextField nombreProductoIngreso;

    @FXML
    private TableColumn<datosUsuarios, String> nombreUsuarioColumnaTabla;

    @FXML
    private TextField nombreUsuarioIngreso;

    @FXML
    private TextField passwordUsuarioIngreso;

    @FXML
    private TableColumn<datosProductos, String> precioProductoColumnaTabla;

    @FXML
    private TextField precioProductoIngreso;

    @FXML
    private Button productosBoton;

    @FXML
    private AnchorPane productosForm;

    @FXML
    private TableView<datosProductos> productosTabla;

    @FXML
    private TableColumn<datosProductos, String> stockProductoColumnaTabla;

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

    public ObservableList<datosProductos> addListaDatosProductos(){

        ObservableList<datosProductos> listaDatosP = FXCollections.observableArrayList();
        String sql = "SELECT * FROM PRODUCTOS";

        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            datosProductos datoProducto;

            while (result.next()){
                datoProducto = new datosProductos(result.getInt("Codigo")
                        , result.getString("Nombre")
                        , result.getFloat("Precio_por_unidad")
                        , result.getInt("Stock"));
                listaDatosP.add(datoProducto);
            }
        }catch (Exception e){e.printStackTrace();}
        return listaDatosP;
    }

    private ObservableList<datosUsuarios> addListaDatosUsuarios;
    private ObservableList<datosProductos> addListaDatosProductos;
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
    public void addListaDatosProductosMostrar(){
        addListaDatosProductos = addListaDatosProductos();

        codigoProductoColumnaTabla.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nombreProductoColumnaTabla.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioProductoColumnaTabla.setCellValueFactory(new PropertyValueFactory<>("precio"));
        stockProductoColumnaTabla.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productosTabla.setItems(addListaDatosProductos);
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

    public void addProductosSelect(){
        datosProductos datoProducto = productosTabla.getSelectionModel().getSelectedItem();
        int num = productosTabla.getSelectionModel().getSelectedIndex();

        if((num - 1) < -1){
            return;
        }

        codigoProductoIngreso.setText(String.valueOf(datoProducto.getCodigo()));
        nombreProductoIngreso.setText(datoProducto.getNombre());
        precioProductoIngreso.setText(String.valueOf(datoProducto.getPrecio()));
        stockProductoIngreso.setText(String.valueOf(datoProducto.getStock()));

    }

    public void displayNombreAdmin(){
        nombreAdmin.setText(getData.adminNombre);
    }
    public void switchForm(ActionEvent event){
        if (event.getSource() == homeBoton){
            displayNombreAdmin();
            homeForm.setVisible(true);
            usuarioForm.setVisible(false);
            productosForm.setVisible(false);
        }else if (event.getSource() == usuariosBoton){
            addListaDatosUsuariosMostrar();
            homeForm.setVisible(false);
            usuarioForm.setVisible(true);
            addListaDatosUsuariosMostrar();
            productosForm.setVisible(false);
        }else if (event.getSource() == productosBoton){
            homeForm.setVisible(false);
            usuarioForm.setVisible(false);
            productosForm.setVisible(true);
            addListaDatosProductosMostrar();
        }
    }
    @FXML
    void actualizarProductoBoton(ActionEvent event) {
        String sql = "UPDATE PRODUCTOS SET Nombre = '"
                + nombreProductoIngreso.getText() + "', Precio_por_unidad = "
                + precioProductoIngreso.getText() + ", Stock = "
                + stockProductoIngreso.getText() + " WHERE Codigo ="
                + codigoProductoIngreso.getText();

        connect = database.connectDb();

        try {
            Alert alert;
            if (codigoProductoIngreso.getText().isEmpty()
                    || nombreProductoIngreso.getText().isEmpty()
                    || precioProductoIngreso.getText().isEmpty()
                    || stockProductoIngreso.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mensaje Eroor");
                alert.setHeaderText(null);
                alert.setContentText("Por favor llenar todos los campos");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Seguro que desea actualizar el Producto: " + codigoProductoIngreso.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensaje Informativo");
                    alert.setHeaderText(null);
                    alert.setContentText("Actualización correcta!");
                    alert.showAndWait();
                    addListaDatosProductosMostrar();
                    limpiarFormularioP();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void actualizarUsuarioBoton(ActionEvent event) {

        String sql = "UPDATE CAJERO SET Nombre = '"
                + nombreUsuarioIngreso.getText() + "', Apellido = '"
                + apellidoUsuarioIngreso.getText() + "', Cedula = '"
                + cedulaUsuarioIngreso.getText() + "', Edad = '"
                + edadUsuarioIngreso.getText() + "', Correo = '"
                + correoUsuarioIngreso.getText() + "', Password = '"
                + passwordUsuarioIngreso.getText() + "' WHERE Usuario ='"
                + usuarioIngreso.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            if (usuarioIngreso.getText().isEmpty()
                    || nombreUsuarioIngreso.getText().isEmpty()
                    || apellidoUsuarioIngreso.getText().isEmpty()
                    || cedulaUsuarioIngreso.getText().isEmpty()
                    || edadUsuarioIngreso.getText().isEmpty()
                    || correoUsuarioIngreso.getText().isEmpty()
                    || passwordUsuarioIngreso.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mensaje Eroor");
                alert.setHeaderText(null);
                alert.setContentText("Por favor llenar todos los campos");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Seguro que desea actualizar el Usuario: " + usuarioIngreso.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensaje Informativo");
                    alert.setHeaderText(null);
                    alert.setContentText("Actualización correcta!");
                    alert.showAndWait();
                    addListaDatosUsuariosMostrar();
                    limpiarFormulario();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void buscarProductoBoton(ActionEvent event) {
        String sql = "SELECT * FROM PRODUCTOS WHERE Codigo = "
                + codigoProductoIngreso.getText();

        connect = database.connectDb();

        try {
            Alert alert;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                prepare = connect.prepareStatement(sql);
                codigoProductoIngreso.setText(result.getString("Codigo"));
                nombreProductoIngreso.setText(result.getString("Nombre"));
                precioProductoIngreso.setText(result.getString("Precio_por_unidad"));
                stockProductoIngreso.setText(result.getString("Stock"));

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mensaje informativo");
                alert.setHeaderText(null);
                alert.setContentText("Usuario encontrado en la base de datos.");
                alert.showAndWait();
                addListaDatosProductosMostrar();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mensaje Error");
                alert.setHeaderText(null);
                alert.setContentText("Producto: " + codigoProductoIngreso.getText() + " no existe en la base de datos.");
                alert.showAndWait();
                codigoProductoIngreso.setText("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void buscarUsuarioBoton(ActionEvent event) {
        String sql = "SELECT * FROM CAJERO WHERE Usuario = '"
                + buscarUsuarioIngreso.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                prepare = connect.prepareStatement(sql);
                usuarioIngreso.setText(result.getString("Usuario"));
                nombreUsuarioIngreso.setText(result.getString("Nombre"));
                apellidoUsuarioIngreso.setText(result.getString("Apellido"));
                cedulaUsuarioIngreso.setText(result.getString("Cedula"));
                edadUsuarioIngreso.setText(result.getString("Edad"));
                correoUsuarioIngreso.setText(result.getString("Correo"));
                passwordUsuarioIngreso.setText(result.getString("Password"));
                buscarUsuarioIngreso.setText("");

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mensaje informativo");
                alert.setHeaderText(null);
                alert.setContentText("Usuario encontrado en la base de datos.");
                alert.showAndWait();
                addListaDatosUsuariosMostrar();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mensaje Error");
                alert.setHeaderText(null);
                alert.setContentText("Usuario: " + usuarioIngreso.getText() + " no existe en la base de datos.");
                alert.showAndWait();
                buscarUsuarioIngreso.setText("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
    void crearProductoBoton(ActionEvent event) {
        String sql = "INSERT INTO PRODUCTOS "
                + "(Nombre, Precio_por_unidad, Stock) "
                + "VALUES(?,?,?)";
        connect = database.connectDb();

        try{
            Alert alert;
            if (codigoProductoIngreso.getText().isEmpty()
                    || nombreProductoIngreso.getText().isEmpty()
                    || precioProductoIngreso.getText().isEmpty()
                    || stockProductoIngreso.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mensaje Error");
                alert.setHeaderText(null);
                alert.setContentText("Por favor llenar todos los campos");
                alert.showAndWait();
            }else{

                String check = "SELECT Codigo FROM PRODUCTOS WHERE Codigo = "
                        +codigoProductoIngreso.getText();

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Mensaje Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Producto: " + codigoProductoIngreso.getText() + " ya existe!");
                    alert.showAndWait();
                }else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, nombreProductoIngreso.getText());
                    prepare.setString(2, precioProductoIngreso.getText());
                    prepare.setString(3,  stockProductoIngreso.getText());
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensaje informativo");
                    alert.setHeaderText(null);
                    alert.setContentText("Creado exitosamente!");
                    alert.showAndWait();
                    addListaDatosProductosMostrar();
                    limpiarFormularioP();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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

    public void limpiarFormularioP(){
        codigoProductoIngreso.setText("");
        nombreProductoIngreso.setText("");
        precioProductoIngreso.setText("");
        stockProductoIngreso.setText("");
    }

    @FXML
    void eliminarProductoBoton(ActionEvent event) {
        String sql = "DELETE FROM PRODUCTOS WHERE Codigo = "
                + codigoProductoIngreso.getText();

        connect = database.connectDb();

        try {

            Alert alert;
            if (codigoProductoIngreso.getText().isEmpty()
                    || nombreProductoIngreso.getText().isEmpty()
                    || precioProductoIngreso.getText().isEmpty()
                    || stockProductoIngreso.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mensaje Error");
                alert.setHeaderText(null);
                alert.setContentText("Por favor llenar todos los campos");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Está seguro de eliminar el Producto: " + codigoProductoIngreso.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    String deleteInfo = "DELETE FROM PRODUCTOS WHERE Codigo = "
                            + codigoProductoIngreso.getText();

                    prepare = connect.prepareStatement(deleteInfo);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensaje informativo");
                    alert.setHeaderText(null);
                    alert.setContentText("Eliminación exitosa!");
                    alert.showAndWait();
                    addListaDatosProductosMostrar();
                    limpiarFormularioP();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void eliminarUsuarioBoton(ActionEvent event) {
        String sql = "DELETE FROM CAJERO WHERE Usuario = '"
                + usuarioIngreso.getText() + "'";

        connect = database.connectDb();

        try {

            Alert alert;
            if (usuarioIngreso.getText().isEmpty()
                    || nombreUsuarioIngreso.getText().isEmpty()
                    || apellidoUsuarioIngreso.getText().isEmpty()
                    || cedulaUsuarioIngreso.getText().isEmpty()
                    || edadUsuarioIngreso.getText().isEmpty()
                    || correoUsuarioIngreso.getText().isEmpty()
                    || passwordUsuarioIngreso.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mensaje Error");
                alert.setHeaderText(null);
                alert.setContentText("Por favor llenar todos los campos");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Está seguro de eliminar el Usuario: " + usuarioIngreso.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    String deleteInfo = "DELETE FROM CAJERO WHERE Usuario = '"
                            + usuarioIngreso.getText() + "'";

                    prepare = connect.prepareStatement(deleteInfo);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensaje informativo");
                    alert.setHeaderText(null);
                    alert.setContentText("Eliminación exitosa!");
                    alert.showAndWait();
                    addListaDatosUsuariosMostrar();
                    limpiarFormulario();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void limpiarProductoBoton(ActionEvent event) {
        limpiarFormularioP();
        addListaDatosProductosMostrar();
    }

    @FXML
    void limpiarUsuarioBoton(ActionEvent event) {
        limpiarFormulario();
        addListaDatosUsuariosMostrar();
    }

    public void initialize(URL location, ResourceBundle resources){
        displayNombreAdmin();
        addListaDatosUsuariosMostrar();
        addListaDatosProductosMostrar();
    }
}//2:20:31
