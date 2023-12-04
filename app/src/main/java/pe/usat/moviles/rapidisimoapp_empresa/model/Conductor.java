package pe.usat.moviles.rapidisimoapp_empresa.model;

import java.util.Date;

public class Conductor {
    private int id;
    private String tipodoc;
    private String numeroDoc;
    private String apellidos;
    private String nombres;
    private String direccion;
    private String fechaNac;
    private String estado;
    private int usuarioID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(String tipodoc) {
        this.tipodoc = tipodoc;
    }

    public String getNumeroDoc() {
        return numeroDoc;
    }

    public void setNumeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }
}
