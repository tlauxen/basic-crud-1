package com.tlauxen.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlauxen.model.Estado;
import com.tlauxen.service.EstadoService;

@Controller
@RequestMapping("/estado")
public class EstadoController extends AbstractCrudController<Estado> {
	
	@Autowired
	private EstadoService estadoService;
	
	@Override
	protected EstadoService getCrudService() {
		return estadoService;
	}

    @RequestMapping("/listagem")
    public String listagem(HttpServletRequest request, HttpServletResponse response) {
    	
    	return "estado.listagem";
    }
    
    @RequestMapping("/cadastro/**")
    public String cadastro(HttpServletRequest request, HttpServletResponse response) {
    	
    	return "estado.cadastro";
    }
    
    @RequestMapping(headers ={"Accept=application/json"}, value="/ajax/find",method=RequestMethod.POST)
    public @ResponseBody List<Estado> find(@RequestBody Estado filter) {
    	
    	return getCrudService().find(filter);
    	
    }
    
}
