package pe.usat.moviles.rapidisimoapp_empresa.response;

import pe.usat.moviles.rapidisimoapp_empresa.model.Solicitud;

public class EstadoClienteResponse {
    private EstadoClienteResponse[] data;
    private String message;
    private boolean status;

    public EstadoClienteResponse[] getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
