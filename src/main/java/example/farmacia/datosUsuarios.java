package example.farmacia;
import java.sql.*;
import javafx.scene.shape.StrokeLineCap;

public class datosUsuarios {
    private String usuario;
    private String nombre;
    private String apellido;
    private String cedula;
    private Integer edad;
    private String correo;
    private String password;

    public datosUsuarios(String usuario, String nombre, String apellido, String cedula, Integer edad, String correo, String password) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.edad = edad;
        this.correo = correo;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public Integer getEdad() {
        return edad;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }
}
