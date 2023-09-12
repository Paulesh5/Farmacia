package example.farmacia;

import javax.sql.rowset.serial.SerialBlob;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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
import com.itextpdf.text.Document;


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
    private String fechaActual;
    @FXML
    public void initialize() {
        String nombreUsuario = getData.nombreUsuario;
        datosUsuarios cajero = obtenerCajeroDesdeBD(nombreUsuario);

        int numeroFactura = obtenerNumeroFacturaSecuencial();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaActual = sdf.format(new Date());

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

    public void generarFacturaPDF(ArrayList<Productofactura> productos, String nombreCajero,
                                  String nombreCliente, String correoCliente, String celularCliente,
                                  String rucCliente, String nombreArchivo){
        try{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaActual = sdf.format(new Date());
        Document document = new Document();
        String ruta = rutaAb(nombreArchivo);
        Blob ruta2 = rutadb(nombreArchivo);
        PdfWriter.getInstance(document, new FileOutputStream(ruta));
        document.open();
        Image logo = Image.getInstance("https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEids1opaIM5ovupmuiquGrgHsa6pVf8o4yyc3PRR8MSBsMP5yBom-So7CPdGgxEgga0G7Mq2s8we1OfxfOpv-nqEx2ciWey8Uf98r9hNQyeWaVm4o1NZqu7Hbb9sBjnlVmEatTVU3AwXkJgjRQSGfgb-fYwzDK4hsDdKK0RZBnQ4blG5UM6LnlwVnQFJdGh/s961/fondo.jpg");
        logo.scaleAbsolute(100,90);
        logo.setAlignment(Chunk.ALIGN_LEFT);
        document.add(logo);

        Paragraph cabecera = new Paragraph();
        cabecera.setAlignment(Element.ALIGN_CENTER);
        document.add(new Paragraph("OWL PHARMA"));
        document.add(new Paragraph("La salud los más importante de la vida"));
        document.add(new Paragraph("RUC: 5543109213"));
        document.add(new Paragraph("Fecha: "+fechaActual));
        document.add(cabecera);

        document.add(Chunk.NEWLINE);

        Paragraph datos = new Paragraph();
        datos.setAlignment(Element.ALIGN_LEFT);
        document.add(new Paragraph("Cliente: " + nombreCliente));
        document.add(new Paragraph("Cedula: "+ rucCliente));
        document.add(new Paragraph("Teléfono/Celular: "+ celularCliente));
        document.add(new Paragraph("Email: "+ correoCliente));
        document.add(datos);

        document.add(Chunk.NEWLINE);
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        PdfPCell nombre = new PdfPCell(new Phrase("Nombre"));
        PdfPCell codigo = new PdfPCell(new Phrase("Codigo"));
        PdfPCell cantidad = new PdfPCell(new Phrase("Cantidad"));
        PdfPCell precio = new PdfPCell(new Phrase("Valor U"));
        PdfPCell subtotal = new PdfPCell(new Phrase("subtotal"));
        tabla.addCell(nombre);
        tabla.addCell(codigo);
        tabla.addCell(cantidad);
        tabla.addCell(precio);
        tabla.addCell(subtotal);
        for(Productofactura act : productos ){
            tabla.addCell(act.getNombre());
            tabla.addCell(String.valueOf(act.getCodigo()));
            tabla.addCell(String.valueOf(act.getStock()));
            tabla.addCell(String.valueOf(act.getPrecio()));
            tabla.addCell(String.valueOf(act.getSubtotal()));
        }
        document.add(tabla);
        document.add(Chunk.NEWLINE);
        Paragraph total = new Paragraph();
        total.setAlignment(Element.ALIGN_RIGHT);
        for(Productofactura act : productos){
            subtotalF = subtotalF + act.getSubtotal();
        }
        iva = subtotalF * 0.12;
        double totalf = iva + subtotalF;

        document.add(new Paragraph("Subtotal: "+ subtotalF));
        document.add(new Paragraph("I.V.A.: "+ iva));
        document.add(new Paragraph("Total: " + totalf));
        document.add(total);
        document.close();
        ProcessBuilder p = new ProcessBuilder();
        p.command("cmd.exe",ruta);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("OWN-PHARMA");
        alert.setContentText("La factura se a creado exitosamente");
        actualizarStockEnBaseDeDatos(productos);
        almancenarpdf(nombreCajero,ruta2,totalf);


        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void CrearFacturaButton(ActionEvent event) {
        String nombreCajero = CajeroLabel.getText();
        String nombreCliente = NombreRespuesta.getText();
        String correoCliente = EmailRespuesta.getText();
        String celularCliente = CelTelRespuesta.getText();
        String rucCliente = CIRUCRespuesta.getText();
        String nombreArchivo = nombreCajero+"-num"+ NumFacLabel.getText() ;

        cajeroController pdfGenerator = new cajeroController();
        actualizarStockEnBaseDeDatos(actualizar);
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

            } else {
                mostrarMensajeError("Por favor, seleccione un producto de la lista.");
            }
        }


    }

    private void agregarProductoAFactura(datosProductos producto) {
        int cantidadP = Integer.parseInt(Cantidad.getText());
        float precioP = (float) producto.getPrecio();
        float Subtotal = cantidadP * precioP;
        int stockD= producto.getStock()-cantidadP;
        System.out.println(stockD);
        Productofactura productof = new Productofactura(producto.getCodigo(), producto.getNombre(), producto.getPrecio(), cantidadP, Subtotal, stockD );
        vista_fac.getItems().add(productof);
        ProductoColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        CodigoProductoColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        CantidadColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PVPColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("precio"));
        SubstotalColumnaCuadro1.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        actualizar.add(productof);

    }

    private void actualizarStockEnBaseDeDatos(ArrayList<Productofactura> productos) {
        try (Connection connection = database.connectDb()) {
            if (connection != null) {

                connection.setAutoCommit(false);

                for (Productofactura producto : productos) {
                    String sql = "UPDATE PRODUCTOS SET stock = ? WHERE codigo = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        int cantidad = producto.getFinalStock();
                        preparedStatement.setInt(1, cantidad);
                        preparedStatement.setInt(2, producto.getCodigo());
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        mostrarMensajeError("Error al ejecutar la actualización en la base de datos.");
                        // Si hay un error, realizar un rollback para deshacer los cambios
                        connection.rollback();
                        return;
                    }
                }


                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al conectar con la base de datos.");
        }
    }

    private  void almancenarpdf(String nombre, Blob pdfurl, double total){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fechaActual = sdf.format(new Date());
        try{
            String sql = "INSERT INTO Facturas (nombre_cajero, fechafacturacion, url_archivo, total_factura) VALUES (?,?,?,?)";
            Connection connection = database.connectDb();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,nombre);
            preparedStatement.setDate(2, java.sql.Date.valueOf(fechaActual));
            preparedStatement.setBlob(3,pdfurl);
            preparedStatement.setDouble(4,total);
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
    private String rutaAb(String nombre){
        String ruta = System.getProperty("user.dir");
        String rutafinal = ruta + "\\facturas_pdf\\"+ nombre + ".pdf";
        return rutafinal;
    }
    private Blob rutadb(String nombre) throws SQLException {
        String ruta = System.getProperty("user.dir");
        String rutafinal = ruta + "\\facturas_pdf\\"+ nombre + ".pdf";
        byte[] bytedata =   rutafinal.getBytes();
        Blob rutaG = new SerialBlob(bytedata);
        return rutaG;
    }


}
