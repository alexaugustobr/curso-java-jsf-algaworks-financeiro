package com.algaworks.cursojsf2.financeiro.repository.infra;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.algaworks.cursojsf2.financeiro.repository.DBException;
import com.algaworks.cursojsf2.financeiro.repository.Lancamentos;
import com.algaworks.cursojsf2.financeiro.service.RegraNegocioException;
import com.algaworks.cursojsf2.util.FacesUtil;

public class LancamentosHibernate implements Lancamentos{
	private Session session;
	
	public LancamentosHibernate(Session session){
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> todos() {
		return session.createCriteria(Lancamento.class)
				.addOrder(Order.desc("dataVencimento"))
				.list();
	}

	@Override
	public Lancamento porCodigo(Integer codigo) {
		return (Lancamento) session.get(Lancamento.class, codigo);
	}

	@Override
	public Lancamento guardar(Lancamento lancamento) {
		return (Lancamento) session.merge(lancamento);
	}
	
	@Override
	public List<Lancamento> todosDaPessoa(Pessoa pessoa){
		return session.createCriteria(Lancamento.class)
				.add(Restrictions.eq("pessoa.codigo", pessoa.getCodigo()))
				.list();
	}
	
	
	
	/*
	 *  
	 * Metodo tambem foi feito com void sem retorno, e tambem funcionou
	 * 
	@Override
	public void guardar(Lancamento lancamento) {
		session.merge(lancamento);
	}
	 * 
	 * 
	 * 
	 */

	@Override
	public void excluir(Lancamento lancamento){
		this.session.delete(lancamento);
	}

	@Override
	public Lancamento comDadosIguais(Lancamento lancamento) {
		//ilike é um select case insensitive
		
		return (Lancamento) this.session.createCriteria(Lancamento.class)
				.add(Restrictions.eq("tipo", lancamento.getTipo()))
				.add(Restrictions.eq("pessoa", lancamento.getPessoa()))
				.add(Restrictions.ilike("descricao", lancamento.getDescricao()))
				.add(Restrictions.eq("valor", lancamento.getValor()))
				.add(Restrictions.eq("dataVencimento", lancamento.getDataVencimento()))
				.uniqueResult();
	}

	@Override
	public boolean lancamentoExiste(Lancamento lancamento) {
		
		
		if ((Lancamento)session.get(Lancamento.class, lancamento.getCodigo())==null){
			System.out.println("Lancamento nao existe");
			return false;
		} else {
			return true;
		}
		
	}
	
}
