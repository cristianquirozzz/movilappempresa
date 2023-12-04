package pe.usat.moviles.rapidisimoapp_empresa.response;

import pe.usat.moviles.rapidisimoapp_empresa.model.Cliente;
import pe.usat.moviles.rapidisimoapp_empresa.model.Conductor;

public class ChoferListadoResponse {
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
