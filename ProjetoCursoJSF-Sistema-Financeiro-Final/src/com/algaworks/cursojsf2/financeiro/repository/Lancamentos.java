package com.algaworks.cursojsf2.financeiro.repository;

import java.util.List;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;

public interface Lancamentos {
	public List<Lancamento> todos();
	public Lancamento porCodigo(Integer codigo);
	public Lancamento guardar(Lancamento lancamento);
	public void excluir(Lancamento lancamento);
	public List<Lancamento> todosDaPessoa(Pessoa pessoa);
	public Lancamento comDadosIguais(Lancamento lancamento);
	public boolean lancamentoExiste(Lancamento lancamento);
}
