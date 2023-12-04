package pe.usat.moviles.rapidisimoapp_empresa.response;

import pe.usat.moviles.rapidisimoapp_empresa.model.Tarifa;

public class TarifaRegistrarResponse {

    private Tarifa[] data;
    private String message;
    private boolean status;

    public Tarifa[] getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
