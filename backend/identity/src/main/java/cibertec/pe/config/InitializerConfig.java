	package cibertec.pe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cibertec.pe.enums.Role;
import cibertec.pe.model.UsuarioCredential;
import cibertec.pe.repository.IUsuarioCredential;

@Component
public class InitializerConfig implements CommandLineRunner {
	@Autowired
  private  PasswordEncoder encoder;
	@Autowired
   private IUsuarioCredential credential;
   

@Override
   public void run(String... args) throws Exception {
     if(credential.count() == 0) {
    	 UsuarioCredential admin = new UsuarioCredential();
    	 admin.setName("admin");
    	 admin.setEmail("admin@gmail.com");
    	 admin.setPassword(encoder.encode("admin123"));
    	 admin.setRol(Role.ADMINISTRADOR);
    	 credential.save(admin);
    	 
    	 UsuarioCredential tecnico = new UsuarioCredential();
    	 tecnico.setName("tecnico");
    	 tecnico.setEmail("tecnico@gmail.com");
    	 tecnico.setPassword(encoder.encode("tecnico123"));
    	 tecnico.setRol(Role.TECNICO);
    	 credential.save(tecnico);
    
    	 UsuarioCredential usuario = new UsuarioCredential();
    	 usuario.setName("usuario");
    	 usuario.setEmail("usuario@gmail.com");
    	 usuario.setPassword(encoder.encode("usuario123"));
    	 usuario.setRol(Role.USUARIO);
    	 credential.save(usuario);
     }
   }
   
}
