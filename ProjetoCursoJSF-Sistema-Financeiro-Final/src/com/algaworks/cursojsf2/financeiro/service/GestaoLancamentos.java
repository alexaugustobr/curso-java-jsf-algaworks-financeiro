package com.algaworks.cursojsf2.financeiro.service;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.repository.DBException;
import com.algaworks.cursojsf2.financeiro.repository.Lancamentos;

//classe de negocio
//não deve conter detalhes da tela exemplo JSF FacesUtil.adicionarMensagem
public class GestaoLancamentos {

	private Lancamentos lancamentos;

	public GestaoLancamentos(Lancamentos lancamentos) {
		this.lancamentos = lancamentos;
	}

	public void excluir(Lancamento lancamento) throws RegraNegocioException, DBException {
		if (this.lancamentoExiste(lancamento)) {
			if (lancamento.isPago()) {
				throw new RegraNegocioException("Lançamento pago não pode ser excluido.");
			} else {
				this.lancamentos.excluir(lancamento);
			}
		} else {
			throw new DBException("Lançamento não existe ou já foi excluido!");
		}

	}

	public void salvar(Lancamento lancamento) throws RegraNegocioException {

		if (this.existeLancamentoSemelhante(lancamento)) {
			throw new RegraNegocioException("Já existe um lançamento igual a este!");
		} else {
			this.lancamentos.guardar(lancamento);
		}

	}
	
	public boolean lancamentoExiste(Lancamento lancamento){
		return lancamentos.lancamentoExiste(lancamento);
	}

	private boolean existeLancamentoSemelhante(Lancamento lancamento) {
		Lancamento lancamentoSemelhante = this.lancamentos.comDadosIguais(lancamento);
		return lancamentoSemelhante != null && !lancamentoSemelhante.equals(lancamento);
	}
}
