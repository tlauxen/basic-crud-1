package com.tlauxen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlauxen.dao.EstadoDao;
import com.tlauxen.model.Estado;

@Service
public class EstadoService implements CrudService<Estado> {

	@Autowired
	private EstadoDao estadoDao;
	
	public Estado saveOrUpdate(Estado estado) {
		if (estado.getId() == null) {
			return estadoDao.create(estado);
		} else {
			return estadoDao.update(estado);
		}
	}

	public List<Estado> find(Estado filter) {
		return estadoDao.find(filter);
	}

	public void remove(Estado model) {
		estadoDao.remove(model);
	}

	public Estado findById(Long id) {
		return estadoDao.findById(id);
	}
	
}
