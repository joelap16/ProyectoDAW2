package com.proyecto.api.dto.error;

public class ErrorResponseDTO {

    private Integer codigo;
    private String mensaje;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(Integer codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}