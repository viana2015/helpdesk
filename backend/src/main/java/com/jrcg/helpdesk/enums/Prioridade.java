package com.jrcg.helpdesk.enums;

public enum Prioridade {

	BAIXA(0,"Baixa"),
	MEDIA(1,"Média"),
	ALTA(2,"Alta"),
	URGENTE(3,"Urgente");
	
	private Integer codigo;
	private String descricao;
	
	
	private Prioridade(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}


	public Integer getCodigo() {
		return codigo;
	}


	public String getDescricao() {
		return descricao;
	}

	public static Prioridade toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (Prioridade x : Prioridade.values()) {
			if (cod.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Prioridade inválido");
	}
	
}
