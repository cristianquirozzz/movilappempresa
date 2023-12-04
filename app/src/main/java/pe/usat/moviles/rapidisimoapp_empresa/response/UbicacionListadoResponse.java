package pe.usat.moviles.rapidisimoapp_empresa.response;

import pe.usat.moviles.rapidisimoapp_empresa.model.Solicitud;
import pe.usat.moviles.rapidisimoapp_empresa.model.Ubicacion;
import pe.usat.moviles.rapidisimoapp_empresa.model.Vehiculo;

public class UbicacionListadoResponse {
    private Ubicacion[] data;
    private String message;
    private boolean status;

    public Ubicacion[] getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
