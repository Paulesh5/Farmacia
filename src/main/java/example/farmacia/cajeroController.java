package example.farmacia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;



public class cajeroController extends loginController{

    @FXML
    private Button BuscarporCodigoButton;

    @FXML
    private Button BuscarporNombreButton;

    @FXML
    private TextField CIRUCRespuesta;

    @FXML
    private Label CajeroLabel;

    @FXML
    private TextField CelTelRespuesta;

    @FXML
    private Button CerrarSesiónButton;

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
    private TableColumn<datosProductos, String> ProductoColumnaCuadro1;

    @FXML
    private TableColumn<datosProductos, Integer> CodigoProductoColumnaCuadro1;
    @FXML
    private TableColumn<datosProductos, Integer> CantidadColumnaCuadro1;

    @FXML
    private TableColumn<datosProductos, Double> PVPColumnaCuadro1;

    @FXML
    private TextField ProductoRespuesta;
    @FXML
    private TextField CodigodeProductoRespuesta;
    @FXML
    private Button SeleccionarButton;


    @FXML
    private Label ValorTotalLabel;

    @FXML
    private TextField Cantidad;

    @FXML
    private TableView<Productofactura> vista_fac;

    @FXML
    private TableView<datosProductos> vista_prod;

    @FXML
    private TableColumn<Productofactura, String> ProductoColumnaCuadro2;

    @FXML
    private TableColumn<Productofactura, Integer> StockColumnaCuadro2;

    @FXML
    private TableColumn<Productofactura, Float> PVPColumnaCuadro2;

    @FXML
    private TableColumn<Productofactura, Float> SubstotalColumnaCuadro1;

    @FXML
    private Label SubtotalC;
    @FXML
    private Label IVAC;
    @FXML
    private Button CalcularBT;
    private float subtotalF = 0;
    private double iva;
    private double total;
    private ArrayList<Productofactura> actualizar = new ArrayList<>();
    @FXML
    public void initialize() {
        String nombreUsuario = getData.nombreUsuario;
        datosUsuarios cajero = obtenerCajeroDesdeBD(nombreUsuario);

        int numeroFactura = obtenerNumeroFacturaSecuencial();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaActual = sdf.format(new Date());

        CajeroLabel.setText(cajero.getNombre());
        NumFacLabel.setText("" + numeroFactura);
        FechaLabel.setText(fechaActual);
    }

    private datosUsuarios obtenerCajeroDesdeBD(String nombreCajero) {
        datosUsuarios cajero = null;

        String sql = "SELECT * FROM CAJERO WHERE Usuario = ?";

        try (Connection connection = database.connectDb();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nombreCajero);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String usuario = resultSet.getString("Usuario");
                    String nombre = resultSet.getString("Nombre");
                    String apellido = resultSet.getString("Apellido");
                    String cedula = resultSet.getString("Cedula");
                    Integer edad = resultSet.getInt("Edad");
                    String correo = resultSet.getString("Correo");
                    String password = resultSet.getString("Password");

                    cajero = new datosUsuarios(usuario, nombre, apellido, cedula, edad, correo, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al obtener los datos del cajero desde la base de datos.");
        }

        return cajero;
    }

    private int obtenerNumeroFacturaSecuencial() {
        int numeroFactura = 0;

        try (Connection connection = database.connectDb();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM Facturas");

            if (resultSet.next()) {
                numeroFactura = resultSet.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al obtener el número de factura secuencial desde la base de datos.");
        }

        return numeroFactura;
    }
    @FXML
    void BuscarporCodigoButton(ActionEvent event) {
        String codigoProducto = CodigodeProductoRespuesta.getText();

        if (codigoProducto.isEmpty()) {
            mostrarMensajeError("Por favor, ingrese un código de producto.");
            return;
        }

        String sql = "SELECT nombre, stock, precio_por_unidad FROM PRODUCTOS WHERE codigo = ?";

        try (Connection connection = database.connectDb();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, codigoProducto);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                vista_prod.getItems().clear();

                if (resultSet.next()) {
                    String nombreProducto = resultSet.getString("nombre");
                    int stockProducto = resultSet.getInt("stock");
                    float precioProducto = resultSet.getFloat("precio_por_unidad");

                    datosProductos producto = new datosProductos(Integer.parseInt(codigoProducto), nombreProducto, precioProducto, stockProducto);

                    vista_prod.getItems().add(producto);

                    ProductoColumnaCuadro2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                    StockColumnaCuadro2.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    PVPColumnaCuadro2.setCellValueFactory(new PropertyValueFactory<>("precio"));
                } else {
                    mostrarMensajeError("No se encontró ningún producto con el código proporcionado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al buscar el producto por código.");
        }
    }
    @FXML
    void BuscarporNombreButton(ActionEvent event) {
        String nombreProducto = ProductoRespuesta.getText();

        if (nombreProducto.isEmpty()) {
            mostrarMensajeError("Por favor, ingrese un nombre de producto.");
            return;
        }

        String sql = "SELECT codigo, nombre, stock, precio_por_unidad FROM PRODUCTOS WHERE nombre LIKE ?";

        try (Connection connection = database.connectDb();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + nombreProducto + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                vista_prod.getItems().clear();

                boolean encontrado = false;

                while (resultSet.next()) {
                    int codigoProducto = resultSet.getInt("codigo");
                    String nombre = resultSet.getString("nombre");
                    int stock = resultSet.getInt("stock");
                    float precio = resultSet.getFloat("precio_por_unidad");

                    datosProductos producto = new datosProductos(codigoProducto, nombre, precio, stock);

                    vista_prod.getItems().add(producto);

                    encontrado = true;
                }

                ProductoColumnaCuadro2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                StockColumnaCuadro2.setCellValueFactory(new PropertyValueFactory<>("stock"));
                PVPColumnaCuadro2.setCellValueFactory(new PropertyValueFactory<>("precio"));

                if (!encontrado) {
                    mostrarMensajeError("No se encontraron productos con el nombre proporcionado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al buscar productos por nombre.");
        }
    }

    @FXML
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

    public void generarFacturaPDF(List<Productofactura> productos, String nombreCajero,
                                  String nombreCliente, String correoCliente, String celularCliente,
                                  String rucCliente, String nombreArchivo) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);


                contentStream.showText("Factura");
                contentStream.newLine();
                contentStream.showText("Cajero: " + nombreCajero);
                contentStream.newLine();
                contentStream.showText("Cliente: " + nombreCliente);
                contentStream.newLine();
                contentStream.showText("Correo: " + correoCliente);
                contentStream.newLine();
                contentStream.showText("Celular: " + celularCliente);
                contentStream.newLine();
                contentStream.showText("RUC: " + rucCliente);
                contentStream.newLine();
                contentStream.newLine();


                for (Productofactura producto : productos) {
                    contentStream.showText("Producto: " + producto.getNombre());
                    contentStream.newLine();
                    contentStream.showText("Precio: " + producto.getPrecio());
                    contentStream.newLine();
                    contentStream.showText("Cantidad: " + producto.getStock());
                    contentStream.newLine();
                    contentStream.showText("Subtotal: " + producto.getSubtotal());
                    contentStream.newLine();
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            // Guardar el documento PDF
            document.save(nombreArchivo + ".pdf");
        }
    }
    @FXML
    void CrearFacturaButton(ActionEvent event) throws IOException {
        String nombreCajero = CajeroLabel.getText();
        String nombreCliente = NombreRespuesta.getText();
        String correoCliente = EmailRespuesta.getText();
        String celularCliente = CelTelRespuesta.getText();
        String rucCliente = CIRUCRespuesta.getText();
        String nombreArchivo = "factura";

        cajeroController pdfGenerator = new cajeroController();
        pdfGenerator.generarFacturaPDF(actualizar, nombreCajero, nombreCliente, correoCliente, celularCliente, rucCliente, nombreArchivo);
    }

    @FXML
    void EliminarProductosButton(ActionEvent event) {

        Productofactura productoSeleccionado = vista_fac.getSelectionModel().getSelectedItem();

        if (productoSeleccionado != null) {
            int cantidadEnFactura = productoSeleccionado.getStock();
            vista_fac.getItems().remove(productoSeleccionado);
            actualizar.remove(productoSeleccionado);

       } else {
           mostrarMensajeError("Por favor, seleccione un producto de la factura para eliminar.");
       }
    }


    @FXML
    void LimpiarConsultaButton(ActionEvent event) {
        ProductoRespuesta.clear();
        CodigodeProductoRespuesta.clear();
        Cantidad.clear();
        vista_prod.getItems().clear();
        subtotalF =0;
    }

    @FXML
    void SeleccionarButton(ActionEvent event) {
        datosProductos productoSeleccionado = vista_prod.getSelectionModel().getSelectedItem();
        if(Integer.valueOf(Cantidad.getText()) >= productoSeleccionado.getStock()){
            mostrarMensajeError("La cantidad ingresara supera el Stock actual");
        }else{
            if (productoSeleccionado != null) {
                agregarProductoAFactura(productoSeleccionado);
                //actualizarStockEnBaseDeDatos(productoSeleccionado);//Se ejecuta esta funcion despues de crear la factura
            } else {
                mostrarMensajeError("Por favor, seleccione un producto de la lista.");
            }
        }


    }

    private void agregarProductoAFactura(datosProductos producto) {
        int cantidadP = Integer.parseInt(Cantidad.getText());
        float precioP = (float) producto.getPrecio();
        float Subtotal = cantidadP * precioP;
        Productofactura productof = new Productofactura(producto.getCodigo(), producto.getNombre(), producto.getPrecio(), cantidadP, Subtotal );
        vista_fac.getItems().add(productof);
        ProductoColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        CodigoProductoColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        CantidadColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("stock")); // Se actualiza el stock en la tabla
        PVPColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("precio"));
        SubstotalColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        actualizar.add(productof);
    }

    private void actualizarStockEnBaseDeDatos(datosProductos producto) {
        int nuevoStock = producto.getStock() - 1;
        String sql = "UPDATE PRODUCTOS SET stock = ? WHERE codigo = ?";

        try (Connection connection = database.connectDb();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, nuevoStock);
            preparedStatement.setInt(2, producto.getCodigo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al actualizar el stock en la base de datos.");
        }
    }
    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    void CalcularBT (ActionEvent event){
        for(Productofactura act: actualizar){
            subtotalF = subtotalF + act.getSubtotal();
        }
        iva = subtotalF * 0.12;
        total = iva + subtotalF;
        IVAC.setText(String.valueOf(iva));
        SubtotalC.setText(String.valueOf(subtotalF));
        ValorTotalLabel.setText(String.valueOf(total));
    }

}
