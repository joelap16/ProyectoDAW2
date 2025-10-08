package cibertec.pe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cibertec.pe.enums.Role;
import cibertec.pe.model.UsuarioCredential;

public interface IUsuarioCredential extends JpaRepository<UsuarioCredential, Integer> {
	
        Optional<UsuarioCredential> findByEmail(String email);

        Optional<UsuarioCredential> findByName(String name);
        
        List<UsuarioCredential> findByRol(Role rol);
}
