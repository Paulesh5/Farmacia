package example.farmacia;

import java.sql.Blob;
import java.sql.Date;

public class datosVentas {
    private Integer id;
    private String nombre;
    private String fecha;
    private Blob url;
    private Double total;

    public datosVentas(Integer id, String nombre, String fecha, Blob url, Double total) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.url = url;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Blob getUrl() {
        return url;
    }

    public void setUrl(Blob url) {
        this.url = url;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
