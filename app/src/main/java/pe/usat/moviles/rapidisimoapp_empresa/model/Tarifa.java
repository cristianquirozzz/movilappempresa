package pe.usat.moviles.rapidisimoapp_empresa.model;

public class Tarifa {
    private int id;
    private double tarifa;
    private String estado;
    private String fechaHoraInicio;
    private String fechaHoraFin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(String fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }
}
