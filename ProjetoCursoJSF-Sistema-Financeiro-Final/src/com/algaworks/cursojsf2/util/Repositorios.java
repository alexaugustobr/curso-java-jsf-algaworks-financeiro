package com.algaworks.cursojsf2.util;

import java.io.Serializable;

import org.hibernate.Session;

import com.algaworks.cursojsf2.financeiro.repository.Lancamentos;
import com.algaworks.cursojsf2.financeiro.repository.Pessoas;
import com.algaworks.cursojsf2.financeiro.repository.infra.LancamentosHibernate;
import com.algaworks.cursojsf2.financeiro.repository.infra.PessoasHibernate;

import util.io.Io;

public class Repositorios implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Pessoas getPessoas(){
		Io.out("Repositorio retornou a instancia da interface de pessoas hibernate");
		return new PessoasHibernate(this.getSession());
		
	}
	
	public Lancamentos getLancamentos(){
		Io.out("Repositorio retornou a instancia da interface de lançamentos hibernate");
		return new LancamentosHibernate(this.getSession());
		
	}
	
	
	private Session getSession(){
		Io.out("criando session para Repositorios");
		return (Session) FacesUtil.getRequestAttribute("session");
	}
	
	
	//metodo recomendado para quando estiver fora do JSF ex testando em modo aplicacao
	public Pessoas getPessoas(Session session){
		Io.out("Repositorio retornou a instancia da interface de pessoas hibernate");
		return new PessoasHibernate(session);
		
	}
	//metodo recomendado para quando estiver fora do JSF ex testando em modo aplicacao
	public Lancamentos getLancamentos(Session session){
		Io.out("Repositorio retornou a instancia da interface de lançamentos hibernate");
		return new LancamentosHibernate(session);
		
	}
}
