package pe.usat.moviles.rapidisimoapp_empresa.response;

import pe.usat.moviles.rapidisimoapp_empresa.model.Conductor;
import pe.usat.moviles.rapidisimoapp_empresa.model.Solicitud;

public class ConductorRegistrarResponse {
    private Conductor[] data;
    private String message;
    private boolean status;

    public Conductor[] getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
