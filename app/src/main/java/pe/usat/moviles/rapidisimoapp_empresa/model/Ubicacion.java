package pe.usat.moviles.rapidisimoapp_empresa.model;

public class Ubicacion {
    private int CONDUCTORid;
    private int SOLICITUD_SERVICIOid;
    private int VEHICULOid;
    private String conductor;
    private String fechaHoraRegistro;
    private Double latitud;
    private Double longitud;
    private String matricula;
    private String nombreEstado;
    private String observacion;

    // Getters

    public int getCONDUCTORid() {
        return CONDUCTORid;
    }

    public void setCONDUCTORid(int CONDUCTORid) {
        this.CONDUCTORid = CONDUCTORid;
    }

    public int getSOLICITUD_SERVICIOid() {
        return SOLICITUD_SERVICIOid;
    }

    public void setSOLICITUD_SERVICIOid(int SOLICITUD_SERVICIOid) {
        this.SOLICITUD_SERVICIOid = SOLICITUD_SERVICIOid;
    }

    public int getVEHICULOid() {
        return VEHICULOid;
    }

    public void setVEHICULOid(int VEHICULOid) {
        this.VEHICULOid = VEHICULOid;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(String fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
