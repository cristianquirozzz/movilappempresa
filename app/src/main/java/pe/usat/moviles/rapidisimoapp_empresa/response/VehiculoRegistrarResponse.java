package pe.usat.moviles.rapidisimoapp_empresa.response;

import pe.usat.moviles.rapidisimoapp_empresa.model.Conductor;
import pe.usat.moviles.rapidisimoapp_empresa.model.Vehiculo;

public class VehiculoRegistrarResponse {
    private Vehiculo[] data;
    private String message;
    private boolean status;

    public Vehiculo[] getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
