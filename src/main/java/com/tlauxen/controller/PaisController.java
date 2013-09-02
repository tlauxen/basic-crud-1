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

import com.tlauxen.model.Pais;
import com.tlauxen.service.PaisService;

@Controller
@RequestMapping("/pais")
public class PaisController extends AbstractCrudController<Pais> {
	
	@Autowired
	private PaisService paisService;
	
	@Override
	protected PaisService getCrudService() {
		return paisService;
	}

    @RequestMapping("/listagem")
    public String listagem(HttpServletRequest request, HttpServletResponse response) {
    	
    	return "pais.listagem";
    }
    
    @RequestMapping("/cadastro/**")
    public String cadastro(HttpServletRequest request, HttpServletResponse response) {
    	
    	return "pais.cadastro";
    }
    
    @RequestMapping(headers ={"Accept=application/json"}, value="/ajax/find",method=RequestMethod.POST)
    public @ResponseBody List<Pais> find(@RequestBody Pais filter) {
    	
    	return getCrudService().find(filter);
    	
    }
    
}
