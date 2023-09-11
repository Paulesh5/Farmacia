package example.farmacia;
public class datosProductos {
    private Integer codigo;
    private String nombre;
    private double precio;
    private Integer stock;


    public datosProductos(Integer codigo, String nombre, double precio, Integer stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() { // Cambiado a double
        return precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

