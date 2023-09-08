package example.farmacia;

import com.sun.javafx.css.StyleManager;
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

import java.sql.*;
import java.util.Optional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class cajeroController extends administradorController{

    @FXML
    private Button BuscarporCodigoButton;

    @FXML
    private Button BuscarporNombreButton;

    @FXML
    private TextField CIRUCRespuesta;

    @FXML
    private Label CajeroLabel;

    @FXML
    private TreeTableColumn<?, ?> CantidadColumna1;

    @FXML
    private TextField CelTelRespuesta;

    @FXML
    private Button CerrarSesiónButton;

    @FXML
    private TreeTableColumn<?, ?> CodProdColumna1;

    @FXML
    private TextField CodigodeProductoRespuesta;

    @FXML
    private Button CrearFacturaButton;

    @FXML
    private Button EliminarProductosButton;

    @FXML
    private TextField EmailRespuesta;

    @FXML
    private Label FechaLabel;

    @FXML
    private Button LimpiarConsultaButton;

    @FXML
    private TextField NombreRespuesta;

    @FXML
    private Label NumFacLabel;

    @FXML
    private TreeTableColumn<?, ?> PVPColumna1;

    @FXML
    private TreeTableColumn<?, ?> ProductoColumna1;

    @FXML
    private TextField ProductoRespuesta;

    @FXML
    private Button SeleccionarButton;

    @FXML
    private TreeTableColumn<?, ?> SubstotalColumna1;

    @FXML
    private Label ValorTotalLabel;

    @FXML
    private Spinner<?> num_stock;

    @FXML
    private TreeTableView<?> vista_fac;

    @FXML
    private TreeTableView<?> vista_prev;

    @FXML
    private TreeTableColumn<?, ?> vista_prod;

    @FXML
    private TreeTableColumn<?, ?> vista_pvp;

    @FXML
    private TreeTableColumn<?, ?> vista_stock;


    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Connection establecerConexion() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/farmacia";
        String USUARIO = "root";
        String CONTRASENA = "gilmar2003";
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }

    public ObservableList<datosProductos> addListaDatosProductos() {
        ObservableList<datosProductos> listaProductos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM PRODUCTOS";

        try {
            Connection connection = establecerConexion();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo");
                String nombre = resultSet.getString("nombre");
                float precio = resultSet.getFloat("precio_por_unidad");
                int stock = resultSet.getInt("stock");

                datosProductos producto = new datosProductos(codigo, nombre, precio, stock);

                listaProductos.add(producto);
            }


            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al cargar los datos de productos.");
        }

        return listaProductos;
    }

    @FXML
    void BuscarporCodigoButton(ActionEvent event) {
        String codigoProducto = CodigodeProductoRespuesta.getText();

        if (codigoProducto.isEmpty()) {
            mostrarMensajeError("Por favor, ingrese un código de producto.");
            return;
        }


        ObservableList<datosProductos> listaProductos = buscarProductoPorCodigo(codigoProducto);

        if (!listaProductos.isEmpty()) {

            cargarDatosEnVistaPrev(listaProductos);
        } else {

            mostrarMensajeError("No se encontró ningún producto con el código proporcionado.");
        }
    }



    private void cargarDatosEnVistaPrev(ObservableList<datosProductos> listaProductos) {
//        vista_prod.setCellValueFactory(codigoProductoColumnaTabla.getCellValueFactory());
//        vista_stock.setCellValueFactory(stockProductoColumnaTabla.getCellValueFactory());
//        vista_pvp.setCellValueFactory(precioProductoColumnaTabla.getCellValueFactory());

//        vista_prev.getRoot().getChildren().clear();


        //for (datosProductos producto : listaProductos) {
          //  TreeItem<datosProductos> item = new TreeItem<>(producto);
        //    vista_prev.getRoot().getChildren().add(item);
      //  }
    }

    public ObservableList<datosProductos> buscarProductoPorCodigo(String codigoProducto) {

        ObservableList<datosProductos> listaProductos = addListaDatosProductos();


        ObservableList<datosProductos> productosFiltrados = FXCollections.observableArrayList();

        for (datosProductos producto : listaProductos) {
            if (String.valueOf(producto.getCodigo()).equals(codigoProducto)) {
                productosFiltrados.add(producto);
            }
        }

        return productosFiltrados;
    }

    @FXML
    void BuscarporNombreButton(ActionEvent event) {

        String nombreProducto = ProductoRespuesta.getText();


        if (nombreProducto.isEmpty()) {
            mostrarMensajeError("Por favor, ingrese un nombre de producto.");
            return;
        }


        ObservableList<datosProductos> productosEncontrados = buscarProductosPorNombre(nombreProducto);

        if (!productosEncontrados.isEmpty()) {

            cargarDatosEnVistaPrev(productosEncontrados);
        } else {

            mostrarMensajeError("No se encontraron productos con el nombre proporcionado.");
        }
    }

    public ObservableList<datosProductos> buscarProductosPorNombre(String nombreProducto) {

        ObservableList<datosProductos> listaProductos = addListaDatosProductos();


        ObservableList<datosProductos> productosFiltrados = FXCollections.observableArrayList();

        for (datosProductos producto : listaProductos) {
            if (producto.getNombre().toLowerCase().contains(nombreProducto.toLowerCase())) {
                productosFiltrados.add(producto);
            }
        }

        return productosFiltrados;
    }

    void CerrarSesiónButton(ActionEvent event) {
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
    void CrearFacturaButton(ActionEvent event) {

        String nombreCajero = CajeroLabel.getText();
        String nombreCliente = NombreRespuesta.getText();
        String ciRucCliente = CIRUCRespuesta.getText();
        String telefonoCliente = CelTelRespuesta.getText();
        String correoCliente = EmailRespuesta.getText();


        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage(PDRectangle.A4);
        documento.addPage(pagina);

        try {

            PDPageContentStream contenido = new PDPageContentStream(documento, pagina);
            contenido.setFont(PDType1Font.HELVETICA_BOLD, 12);


            contenido.beginText();
            contenido.newLineAtOffset(50, 700);
            contenido.showText("Factura");
            contenido.newLine();
            contenido.showText("Cajero: " + nombreCajero);
            contenido.newLine();
            contenido.showText("Cliente: " + nombreCliente);
            contenido.newLine();
            contenido.showText("CI/RUC: " + ciRucCliente);
            contenido.newLine();
            contenido.showText("Teléfono: " + telefonoCliente);
            contenido.newLine();
            contenido.showText("Correo electrónico: " + correoCliente);
            contenido.newLine();
            contenido.endText();


            String nombrePDF = "factura_" + System.currentTimeMillis() + ".pdf";
            documento.save(nombrePDF);
            documento.close();

            mostrarMensaje("Factura creada y guardada como " + nombrePDF);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeError("Error al crear la factura.");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void EliminarProductosButton(ActionEvent event) {
        int selectedIndex = vista_fac.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            if (selectedIndex < listaProductosFactura.size()) {

                listaProductosFactura.remove(selectedIndex);


                cargarProductosEnVistaFac(listaProductosFactura);

                mostrarMensaje("Producto eliminado de la factura.");
            }
        } else {
            mostrarMensajeError("Por favor, seleccione un producto de la factura para eliminar.");
        }
    }

    private ObservableList<datosProductos> listaProductosFactura = FXCollections.observableArrayList();




    private void cargarProductosEnVistaFac(ObservableList<datosProductos> listaProductos) {

        vista_fac.getRoot().getChildren().clear();


        for (datosProductos producto : listaProductos) {
            TreeItem<datosProductos> item = new TreeItem<>(producto);
            vista_fac.getRoot().getChildren().add(item);
        }
    }



    @FXML
    void LimpiarConsultaButton(ActionEvent event) {

        ProductoRespuesta.clear();
        CodigodeProductoRespuesta.clear();
        vista_prev.getRoot().getChildren().clear();
    }


    @FXML
    void SeleccionarButton(ActionEvent event) {
        TreeItem<?> productoSeleccionado = vista_prev.getSelectionModel().getSelectedItem();

        if (productoSeleccionado != null) {

            listaProductosFactura.add(productoSeleccionado);


            cargarProductosEnVistaFac(listaProductosFactura);

            mostrarMensaje("Producto agregado a la factura: " + productoSeleccionado.getValue().getNombre());
        } else {
            mostrarMensajeError("Por favor, seleccione un producto para agregar a la factura.");
        }
    }
    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
