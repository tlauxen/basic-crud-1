package com.tlauxen.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
    @RequestMapping("/")
    public String home(HttpServletRequest request, HttpServletResponse response) {
    	
    	return "index";
    }
    
    @RequestMapping("/testarConexao")
    public String testarConexao(HttpServletRequest request, HttpServletResponse response, Model model) {
    	
//    	DBI dbi = new DBI(dataSource);
//    	Handle h = dbi.open();
//    	h.execute("create table moeda (idmoeda integer, descricao varchar);");
//    	h.execute("insert into moeda (idmoeda, descricao) values (?,?)", 1, "Real");
//    	h.execute("insert into moeda (idmoeda, descricao) values (?,?)", 2, "Dolar");
//    	Query<Map<String, Object>> q = h.createQuery("select descricao from moeda");
//    	Query<String> map = q.map(StringMapper.FIRST);
//    	List<String> list = map.list();
//    	model.addAttribute("mensagem", list.toString());
//    	h.execute("drop table moeda;");
//    	h.close();

    	return "index";
    }
    
    public static void main(String[] args) {
		System.out.println(Long.MAX_VALUE);
	}
    
}
