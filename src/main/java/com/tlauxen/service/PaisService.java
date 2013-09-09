package com.tlauxen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlauxen.dao.PaisDao;
import com.tlauxen.model.Pais;

@Service
public class PaisService implements CrudService<Pais> {

	@Autowired
	private PaisDao paisDao;
	
	public Pais saveOrUpdate(Pais pais) {
		if (pais.getId() == null) {
			return paisDao.create(pais);
		} else {
			return paisDao.update(pais);
		}
	}

	public List<Pais> find(Pais filter) {
		return paisDao.find(filter);
	}

	public void remove(Pais model) {
		paisDao.remove(model);
	}

	public Pais findById(Long id) {
		return paisDao.findById(id);
	}
	
}
