package com.algaworks.cursojsf2.financeiro.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.algaworks.cursojsf2.financeiro.model.TipoLancamento;
import com.algaworks.cursojsf2.financeiro.repository.Pessoas;
import com.algaworks.cursojsf2.util.FacesUtil;
import com.algaworks.cursojsf2.util.HibernateUtil;
import com.algaworks.cursojsf2.util.Repositorios;

import util.io.Io;
import util.string.FormatarString;

@ManagedBean
@ViewScoped
public class CadastroPessoaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pessoa pessoa = new Pessoa();
	private Repositorios repositorios = new Repositorios();

	public void setPessoa(Pessoa pessoa) throws CloneNotSupportedException {
		if(pessoa==null){
			this.pessoa = new Pessoa();
		} else {
			this.pessoa = (Pessoa) pessoa.clone();
		}
		
	}
	
	public boolean isEditando(){
		//return this.pessoa.getCodigo()!=null; 
		if (this.pessoa.getCodigo()!=null){
			return true;
		} else {
			return false;
		}
	}

	
	@PostConstruct
	public void init() {
		Io.out("init cadastro de pessoas");
		/*
		Io.out("\nbuscando pessoas no banco");
		//GestaoPessoas gestaoPessoas = new GestaoPessoas();
		//this.pessoas = gestaoPessoas.listarTodas();
		Session session = HibernateUtil.getSessition();
		//seleciona pessoa e poe a ordem ascendente por nome
		this.pessoas = session.createCriteria(Pessoa.class)
				.addOrder(Order.asc("nome"))
				.list();
		//transaction fez com que atualizasse a lista de pessoas
		Transaction trx = session.beginTransaction();
		trx.commit();
		session.close();
		*/
	}
	
	public void formatarNome(){
		pessoa.setNome(FormatarString.removerEspacosDuplicados(pessoa.getNome()));
	}
	
	public void cadastrar() {
		this.formatarNome();
		Pessoas pessoaDAO = repositorios.getPessoas();
		pessoaDAO.guardar(this.pessoa);
		Io.out("Pessoa "+pessoa.getNome()+" foi cadastrada!");

		this.pessoa = new Pessoa();
		
		String msg = "Cadastro de Pessoa efetuado com sucesso!";
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
	}
	
	public TipoLancamento[] getTiposLancamentos() {
		return TipoLancamento.values();
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
	
}