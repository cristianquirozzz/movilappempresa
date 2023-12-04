package pe.usat.moviles.rapidisimoapp_empresa.response;

import pe.usat.moviles.rapidisimoapp_empresa.model.Estado;

public class EstadoRegistrarResponse {
    private Estado[] data;
    private String message;
    private boolean status;

    public Estado[] getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
