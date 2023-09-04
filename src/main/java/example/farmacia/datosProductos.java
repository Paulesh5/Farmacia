package example.farmacia;

public class datosProductos {
    private Integer codigo;
    private String nombre;
    private float precio;
    private Integer stock;

    public datosProductos(Integer codigo, String nombre, float precio, Integer stock) {
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

    public float getPrecio() {
        return precio;
    }

    public Integer getStock() {
        return stock;
    }
}
