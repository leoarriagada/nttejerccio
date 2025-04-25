package cl.ntt.postulacion.api.usuario.model;

import java.util.List;

public class UsuarioPost {
    private String nombre;
    private String correo;
    private String contraseña;
    private List<Telefono> telefonos;

    private int id;

    public UsuarioPost(String nombre, String correo, String contraseña, List<Telefono> telefonos) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefonos = telefonos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
