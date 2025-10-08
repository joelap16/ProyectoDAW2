package cibertec.pe.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import cibertec.pe.util.JwtUtil;
import io.jsonwebtoken.Claims;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
	
	@Autowired
	private RouteValidator validator;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public AuthenticationFilter() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
	    return ((exchange, chain) -> {
	        if (validator.isSecured.test(exchange.getRequest())) {
	            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
	                throw new RuntimeException("Missing authorization header");
	            }

	            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
	            if (authHeader != null && authHeader.startsWith("Bearer ")) {
	                authHeader = authHeader.substring(7);
	            }

	            try {
	                jwtUtil.validateToken(authHeader);
	                Claims claims = jwtUtil.getClaims(authHeader);
	                String rol = claims.get("rol", String.class);
	                String path = exchange.getRequest().getURI().getPath();

	                // Reglas por ruta
	                if (path.startsWith("/api/admin") && !rol.equals("ADMINISTRADOR")) {
	                    throw new RuntimeException("Acceso denegado: se requiere rol ADMINISTRADOR");
	                } else if (path.startsWith("/api/tecnico") && !rol.equals("TECNICO")) {
	                    throw new RuntimeException("Acceso denegado: se requiere rol TECNICO");
	                } else if (path.startsWith("/api/usuario") && !rol.equals("USUARIO")) {
	                    throw new RuntimeException("Acceso denegado: se requiere rol USUARIO");
	                }

	            } catch (Exception e) {
	                System.out.println("invalid access...!");
	                throw new RuntimeException("Unauthorized access to application");
	            }
	        }
	        return chain.filter(exchange);
	    });
	}
		
	public static class Config{
		
	}
}
