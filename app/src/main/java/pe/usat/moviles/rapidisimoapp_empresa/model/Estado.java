package pe.usat.moviles.rapidisimoapp_empresa.model;

public class Estado {
    private int id;
    private String nombre;
    private String fechaHoraRegistro;
    private String observacion;
    private String estado;
    private int solicitud_servicioid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(String fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getSolicitud_servicioid() {
        return solicitud_servicioid;
    }

    public void setSolicitud_servicioid(int solicitud_servicioid) {
        this.solicitud_servicioid = solicitud_servicioid;
    }
}
