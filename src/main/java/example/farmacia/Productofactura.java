package example.farmacia;

public class Productofactura {
    private Integer codigo;
    private String nombre;
    private double precio;
    private Integer stock;
    private float subtotal;



    public Productofactura(Integer codigo, String nombre, double precio, Integer stock, float subtotal) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.subtotal = subtotal;
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

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public Productofactura() {
    }
}
