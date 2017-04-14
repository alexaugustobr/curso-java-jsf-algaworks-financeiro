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

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.algaworks.cursojsf2.financeiro.model.TipoLancamento;
import com.algaworks.cursojsf2.financeiro.repository.Pessoas;
import com.algaworks.cursojsf2.financeiro.service.GestaoLancamentos;
import com.algaworks.cursojsf2.financeiro.service.RegraNegocioException;
import com.algaworks.cursojsf2.util.FacesUtil;
import com.algaworks.cursojsf2.util.Repositorios;

import util.io.Io;

@ManagedBean
@ViewScoped
public class CadastroLancamentoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private Lancamento lancamento = new Lancamento();
	private Repositorios repositorios = new Repositorios();
	
	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) throws CloneNotSupportedException {
		if(lancamento==null){
			Io.out("Lançamento é nulo criando um novo");
			//instancia uma novo se o mesmo é nulo
			//para conter o problema do conversor passando um valor nulo
			this.lancamento = new Lancamento();
		} else {
			//this.lancamento = lancamento;
			//cria um clone que esta desconectado do banco de dados
			//serve para as telas de alteração
			//caso contrario o usuario consegue editar um objeto existente e
			//deixar com os mesmos valores de um outro objeto do BD
			//Ao salvar nao vai refletir no bd automaticamente
			//vai passar pelos testes para ver se nao a outro objeto quase igual no bd
			
			this.lancamento = (Lancamento) lancamento.clone();
			Io.out("Lançamento foi clonado");
		}
		
	}

	@PostConstruct
	public void init() {
		//Io.out("\nbuscando pessoas no banco");
		Pessoas pessoas = this.repositorios.getPessoas();
		this.pessoas = pessoas.todas();// abrir implementacao
	}
	

	// funcionando junto ao imediate para passar o valor do evento de forma imediata
	// atibuto imediate+javascript subimit ou ajax
	public void lancamentoPagoModificado(ValueChangeEvent event) {
		
		this.lancamento.setPago((Boolean) event.getNewValue());
		this.lancamento.setDataPagamento(null);
		Io.out("Esta pago"+lancamento.isPago());
		//passa os atributos alterados para o componente
		FacesContext.getCurrentInstance().renderResponse();
	}

	public void salvar() {
		GestaoLancamentos gestaoLancamentos = new GestaoLancamentos(this.repositorios.getLancamentos());
		try {
			gestaoLancamentos.salvar(this.lancamento);
			this.lancamento = new Lancamento();
			String msg;
			String origem = "Cadastro Lancamento Bean";
			if (isEditando()){
				msg = "Alterações no lançamento foram salvas!";
			} else{
				//msg = "Lançamento foi cadastrado com sucesso!";
				msg = FacesUtil.getMensagemI18n("entry_saved");
			}
			Io.out(msg,origem);
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_INFO, msg, msg);

		} catch (RegraNegocioException e) {
			String msg = e.getMessage();
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, msg, msg);		
			}
	}

	public TipoLancamento[] getTiposLancamentos() {
		return TipoLancamento.values();
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
	public boolean isEditando(){
		//Io.out("Esta editando? ");
		//Io.out(this.lancamento.getCodigo()!=null);
		Io.out("Esta editando?"+" "+(this.lancamento.getCodigo()!=null));
		return this.lancamento.getCodigo()!=null;
	}

}