package example.farmacia;

public class Productofactura {
    private Integer codigo;
    private String nombre;
    private double precio;
    private Integer stock;
    private float subtotal;
    private Integer finalStock;



    public Productofactura(Integer codigo, String nombre, double precio, Integer stock, float subtotal,Integer finalStock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.subtotal = subtotal;
        this.finalStock = finalStock;
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

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFinalStock() {
        return finalStock;
    }

    public void setFinalStock(Integer finalStock) {
        this.finalStock = finalStock;
    }

    public Productofactura() {
    }
}
