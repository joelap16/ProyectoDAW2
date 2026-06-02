package com.proyecto.api.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.proyecto.api.dto.error.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura cualquier RuntimeException
    // Ejemplo:
    // throw new RuntimeException("Usuario no encontrado");
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> manejarRuntime(RuntimeException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    // Captura cualquier excepción no controlada
    // Sirve como última barrera para evitar mostrar errores internos
    // 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> manejarGeneral(Exception ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocurrió un error interno"
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    // Captura errores de validación generados por @Valid
    // Ejemplo:
    // @NotBlank
    // @Email
    // @NotNull
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        // Obtener todos los mensajes de error
        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        // Construir respuesta personalizada
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("codigo", 400);
        respuesta.put("errores", errores);

        return ResponseEntity.badRequest().body(respuesta);
    }
}