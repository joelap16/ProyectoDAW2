package com.proyecto.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.api.model.Categoria;
import com.proyecto.api.model.Tecnico;
import com.proyecto.api.repository.CategoriaRepository;
import com.proyecto.api.repository.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	TecnicoRepository reposTecnico;
	
	@Autowired
	CategoriaRepository reposCategorias;
	
	public List<Tecnico> listarTecnicos(){
		return reposTecnico.findAll();
	}
	
	public void asignarCategoriaATecnico(int idTecnico, int idCategoria) {
        Tecnico tecnico = reposTecnico.findById(idTecnico)
            .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        Categoria categoria = reposCategorias.findById(idCategoria)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        tecnico.setCategoria(categoria);
        reposTecnico.save(tecnico);
    }
}
