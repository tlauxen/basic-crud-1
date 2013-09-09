package com.tlauxen.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.springframework.stereotype.Repository;

import com.tlauxen.model.Estado;
import com.tlauxen.model.Pais;

@Repository
public class EstadoDao extends AbstractCrudDao<Estado> {
	
	@Override
	protected Class<Estado> getEntityClass() {
		return Estado.class;
	}
	
	public List<Estado> find(Estado filter) {
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" select e.id as e_id, e.sigla as e_sigla, e.nome as e_nome, p.id as p_id, p.sigla as p_sigla, p.nome as p_nome ");
		sql.append(" from estado e inner join pais p on p.id = e.idpais ");
		
		if (StringUtils.isNotBlank(filter.getSigla())) {
			sql.append(" and upper(e.sigla) like upper(?) ");
			params.add("%"+filter.getSigla()+"%");
		}
		
		if (StringUtils.isNotBlank(filter.getNome())) {
			sql.append(" and upper(e.nome) like upper(?) ");
			params.add("%"+filter.getNome()+"%");
		}
		if (filter.getPais() != null && filter.getPais().getId() != null) {
			sql.append(" and p.id = ? ");
			params.add("%"+filter.getNome()+"%");
		}
		sql.append(" order by p.sigla,p.nome,e.sigla,e.nome");
		
    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
    		List<Estado> toReturn = new ArrayList<Estado>();
    		List<Map<String, Object>> rs = h.select(sql.toString(), params.toArray(new Object[]{}));
    		for (Map<String,Object> row: rs) {
    			Estado e = new Estado();
    			e.setId((Long) row.get("e_id"));
    			e.setSigla((String)row.get("e_sigla"));
    			e.setNome((String)row.get("e_nome"));
    			e.setPais(new Pais());
    			e.getPais().setId((Long)row.get("p_id"));
    			e.getPais().setSigla((String)row.get("p_sigla"));
    			e.getPais().setNome((String)row.get("p_nome"));
    			
    			toReturn.add(e);
    		}
    		return toReturn;
    	} finally {
    		h.close();
    	}
	}
	
}
