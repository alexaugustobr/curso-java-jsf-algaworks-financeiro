package com.algaworks.cursojsf2.financeiro.repository;

import java.util.List;

import com.algaworks.cursojsf2.financeiro.model.*;

public interface Pessoas {
	public List<Pessoa> todas();
	public List<Pessoa> todas(int limiteLinhasPorPagina, int linhaInicial);
	public Pessoa porCodigo(Integer codigo);
	public void deletar(Pessoa pessoa);
	public void guardar(Pessoa pessoa);
	public Long quantidadeDeRegistros();
	public int quantidadeDePaginas(int limiteLinhasPorPagina);
	public Pessoa porNumLinha(int index);
}
