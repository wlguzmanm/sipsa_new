package co.gov.dane.sipsa.backend.model;

public class RequestStatusViewModel {
    private String status;
    private String message;
    private Boolean validar;
    private String imei;
    private String usuario;
    private String fkManzana;
    private String uuidEncuesta;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getValidar() {
        return validar;
    }

    public void setValidar(Boolean validar) {
        this.validar = validar;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFkManzana() {
        return fkManzana;
    }

    public void setFkManzana(String fkManzana) {
        this.fkManzana = fkManzana;
    }

    public String getUuidEncuesta() {
        return uuidEncuesta;
    }

    public void setUuidEncuesta(String uuidEncuesta) {
        this.uuidEncuesta = uuidEncuesta;
    }
}
