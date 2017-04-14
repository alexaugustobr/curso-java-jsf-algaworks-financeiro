package com.algaworks.cursojsf2.financeiro.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.algaworks.cursojsf2.financeiro.repository.DBException;
import com.algaworks.cursojsf2.financeiro.repository.Lancamentos;
import com.algaworks.cursojsf2.financeiro.service.GestaoLancamentos;
import com.algaworks.cursojsf2.financeiro.service.RegraNegocioException;
import com.algaworks.cursojsf2.util.FacesUtil;
import com.algaworks.cursojsf2.util.HibernateUtil;
import com.algaworks.cursojsf2.util.Repositorios;

import util.io.Io;

@ManagedBean
public class ConsultaLancamentoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	private Lancamento lancamentoSelecionado;
	private Repositorios repositorios = new Repositorios();

	@PostConstruct
	public void inicializar() {
		Io.out("Carregando a tela e buscando lancamentos");
		Lancamentos lancamentos = this.repositorios.getLancamentos();
		this.lancamentos = lancamentos.todos();
		
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void excluir() {
		Io.out("lançamento " + lancamentoSelecionado.isPago());
		// buscando repositorio de lançamentos
		GestaoLancamentos gestaoLancamentos = new GestaoLancamentos(this.repositorios.getLancamentos());
		try {
			gestaoLancamentos.excluir(this.lancamentoSelecionado);
			this.inicializar(); 
			// Se não passou pela excessão ele cai nessa mensagem
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Excluido o lançamento!", "Excluido o lançamento!");
		} catch (RegraNegocioException e) {
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
		} catch (DBException e) {
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
		}
	}

	public Lancamento getLancamentoSelecionado() {
		return lancamentoSelecionado;
	}

	public void setLancamentoSelecionado(Lancamento lancamentoSelecionado) {
		this.lancamentoSelecionado = lancamentoSelecionado;
	}

}