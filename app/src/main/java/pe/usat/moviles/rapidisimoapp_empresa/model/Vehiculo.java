package pe.usat.moviles.rapidisimoapp_empresa.model;

public class Vehiculo {
    private int id;
    private String matricula;
    private double capacidadTotal;
    private String tipoCarga;
    private String modelo;
    private String marca;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public double getCapacidadTotal() {
        return capacidadTotal;
    }

    public void setCapacidadTotal(double capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
