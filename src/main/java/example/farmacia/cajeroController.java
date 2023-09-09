package example.farmacia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



import javafx.scene.control.Label;
import javafx.scene.control.TextField;
public class cajeroController {

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
    private Spinner<datosProductos> num_stock;

    @FXML
    private TableView<datosProductos> vista_fac;

    @FXML
    private TableView<datosProductos> vista_prod;

    @FXML
    private TableColumn<datosProductos, String> ProductoColumnaCuadro2;

    @FXML
    private TableColumn<datosProductos, Integer> StockColumnaCuadro2;

    @FXML
    private TableColumn<datosProductos, Float> PVPColumnaCuadro2;

    @FXML
    private TableColumn<datosProductos, Float> SubstotalColumnaCuadro1;


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

    @FXML
    void CrearFacturaButton(ActionEvent event) {

    }

    @FXML
    void EliminarProductosButton(ActionEvent event) {

        //datosProductos productoSeleccionado = vista_fac.getSelectionModel().getSelectedItem();

      //  if (productoSeleccionado != null) {

      //      int cantidadEnFactura = productoSeleccionado.getStock();




    //        vista_fac.getItems().remove(productoSeleccionado);


    //        actualizarStockEnBaseDeDatos(productoSeleccionado, cantidadEnFactura);
    //    } else {
    //        mostrarMensajeError("Por favor, seleccione un producto de la factura para eliminar.");
   //     }
    }


    @FXML
    void LimpiarConsultaButton(ActionEvent event) {
        num_stock.getValueFactory().setValue(null);
        ProductoRespuesta.clear();
        CodigodeProductoRespuesta.clear();
    }

    @FXML
    void SeleccionarButton(ActionEvent event) {
        datosProductos productoSeleccionado = vista_prod.getSelectionModel().getSelectedItem();

        if (productoSeleccionado != null) {
            agregarProductoAFactura(productoSeleccionado);
            actualizarStockEnBaseDeDatos(productoSeleccionado);
        } else {
            mostrarMensajeError("Por favor, seleccione un producto de la lista.");
        }
    }

    private void agregarProductoAFactura(datosProductos producto) {
        vista_fac.getItems().add(producto);
        ProductoColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        CodigoProductoColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        CantidadColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("stock")); // Se actualiza el stock en la tabla
        PVPColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("precio"));
        SubstotalColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    private void actualizarStockEnBaseDeDatos(datosProductos producto) {
        int nuevoStock = producto.getStock() - 1; // Resta 1 al stock actual del producto
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

}
