package com.tlauxen.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.springframework.stereotype.Repository;

import com.tlauxen.model.Pais;

@Repository
public class PaisDao extends AbstractCrudDao<Pais> {
	
	@Override
	protected Class<Pais> getEntityClass() {
		return Pais.class;
	}
	
	public List<Pais> find(Pais filter) {
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select id,sigla,nome from pais where 1=1");
		
		if (StringUtils.isNotBlank(filter.getSigla())) {
			sql.append(" and upper(sigla) like upper(?)");
			params.add("%"+filter.getSigla()+"%");
		}
		
		if (StringUtils.isNotBlank(filter.getNome())) {
			sql.append(" and upper(nome) like upper(?)");
			params.add("%"+filter.getNome()+"%");
		}
		sql.append(" order by sigla,nome");
		
    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
    		List<Pais> toReturn = new ArrayList<Pais>();
    		List<Map<String, Object>> rs = h.select(sql.toString(), params.toArray(new Object[]{}));
    		for (Map<String,Object> row: rs) {
    			Pais pais = new Pais();
    			pais.setId((Long) row.get("id"));
    			pais.setSigla((String) row.get("sigla"));
    			pais.setNome((String) row.get("nome"));
    			
    			toReturn.add(pais);
    		}
    		return toReturn;
    	} finally {
    		h.close();
    	}
	}
	
}
