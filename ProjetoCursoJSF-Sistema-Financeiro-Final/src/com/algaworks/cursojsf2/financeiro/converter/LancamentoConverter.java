package com.algaworks.cursojsf2.financeiro.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.repository.Lancamentos;
import com.algaworks.cursojsf2.util.Repositorios;

@FacesConverter(forClass=Lancamento.class)
public class LancamentoConverter implements Converter {
	private Repositorios repositorios = new Repositorios();
	//busca na lista de pessoas com base no value = codigo da pessoa
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Lancamento retorno = null;
		if (value != null && !value.equals("")) {
			Lancamentos lancamentos = repositorios.getLancamentos();
			retorno = lancamentos.porCodigo(new Integer(value));
			if(retorno==null){
				String descricaoErro = "Lancamento não exisste.";
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,descricaoErro,descricaoErro);
				throw new ConverterException(message);
			}
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			//metodo antigo não funciona quando  usado via parametro
			//ao tentar salvar o objeto na tela da erro
			//return ((Lancamento) value).getCodigo().toString();
			Integer codigo =((Lancamento) value).getCodigo();
			return codigo == null  ? "":codigo.toString();
		}
		return null;
	}
}
