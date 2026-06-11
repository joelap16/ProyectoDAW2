package cibertec.pe.dto.api;

import lombok.Data;

@Data
public class UsuarioApiRequest {

    private String nombre;
    private String apellido;
    private String email;
    private Integer rolId;

    public UsuarioApiRequest() {
    }

	public UsuarioApiRequest(String nombre, String apellido, String email, Integer rolId) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.rolId = rolId;
	}

}