package com.tlauxen.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data 
@EqualsAndHashCode(callSuper=false,of={"id"}) 
@ToString(callSuper=true)
public class Estado extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 7387474294475539667L;
	
	private Long id;
	private Pais pais;
	private String sigla;
	private String nome;
	
}
