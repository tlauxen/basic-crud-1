package com.tlauxen.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tlauxen.model.Pais;
import com.tlauxen.service.PaisService;

@Controller
@RequestMapping("/pais")
public class PaisController {
	
	@Autowired
	private PaisService paisService;

    @RequestMapping("/listagem")
    public String listagem(HttpServletRequest request, HttpServletResponse response) {
    	
    	return "pais.listagem";
    }
    

    @RequestMapping("/cadastro/**")
    public String cadastro(HttpServletRequest request, HttpServletResponse response) {
    	
    	return "pais.cadastro";
    }
    
    @RequestMapping(headers ={"Accept=application/json"}, value="/ajax/findById",method=RequestMethod.GET)
    public @ResponseBody Pais findById(@RequestParam Long id) {
    	
    	return paisService.findById(id);
    	
    }
    
    @RequestMapping(headers ={"Accept=application/json"}, value="/ajax/save",method=RequestMethod.POST)
    public @ResponseBody Pais save(@RequestBody() Pais model) {
    	
    	return paisService.saveOrUpdate(model);
    	
    }
    
    @RequestMapping(headers ={"Accept=application/json"}, value="/ajax/find",method=RequestMethod.POST)
    public @ResponseBody List<Pais> find(@RequestBody Pais filter) {
    	
    	return paisService.find(filter);
    	
    }
    
    @RequestMapping(value="/ajax/remove",method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void remove(@RequestBody() Pais model) {
    	
    	paisService.remove(model);
    	
    }
    
}
