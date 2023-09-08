package example.farmacia;
public class datosProductos {
    public Integer codigo;
    public String nombre;
    public double precio;
    public Integer stock;

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
}