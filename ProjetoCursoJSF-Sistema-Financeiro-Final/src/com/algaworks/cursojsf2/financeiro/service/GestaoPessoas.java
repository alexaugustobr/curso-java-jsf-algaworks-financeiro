package com.algaworks.cursojsf2.financeiro.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.algaworks.cursojsf2.financeiro.repository.DBException;
import com.algaworks.cursojsf2.financeiro.repository.Lancamentos;
import com.algaworks.cursojsf2.financeiro.repository.Pessoas;

import util.io.Io;

public class GestaoPessoas {

	private Lancamentos lancamentos;
	private Pessoas pessoas;
	private Pessoa pessoaSelecionada;

	public GestaoPessoas(Pessoas pessoas , Lancamentos lancamentos){
		this.lancamentos = lancamentos; 
		this.pessoas = pessoas;
	}

	public void excluir(Pessoa pessoa) throws RegraNegocioException, DBException {	
		if(this.pessoaExiste(pessoa)){
			if(possuiLancamentos(pessoa)){
				throw new RegraNegocioException("A pessoa possui lançamentos, não pode ser excluida!");
			} else {
				this.pessoas.deletar(pessoaSelecionada);
				pessoaSelecionada=null;
			}
			
		} else {
			throw new DBException("Pessoa não existe ou já foi excluida por outro usuário!");
		}
	}

	public boolean possuiLancamentos(Pessoa pessoa) {
		List<Lancamento> lancamentos = this.lancamentos.todosDaPessoa(pessoa);

		if (lancamentos.isEmpty()) {
			Io.out("Não a lançamentos para a o " + pessoa.getNome());
			return false;
		} else {
			Io.out(pessoa.getNome() + " possui lançamentos");
			for (Lancamento l : lancamentos) {
				Io.out("Codigo do Lançamento: " + l.getCodigo() + " - " + l.getDescricao());
			}
			return true;

		}
	}

	public void salvar(Pessoa pessoa) {

		// if (this.existeLancamentoSemelhante(pessoa)) { throws
		// RegraNegocioException
		// throw new RegraNegocioException("Já existe um lançamento igual a
		// este!");
		// } else {
		//this.pessoas.guardar(pessoa);
		// }
		this.pessoas.guardar(pessoa);
	}

	public boolean pessoaExiste(Pessoa pessoa) {
		pessoaSelecionada = this.pessoas.porCodigo(pessoa.getCodigo());

		if (pessoaSelecionada == null) {
			return false;
		} else {
			return true;
		}

	}

}
