package com.algaworks.cursojsf2.financeiro.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.hibernate.Session;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.algaworks.cursojsf2.financeiro.repository.Pessoas;
import com.algaworks.cursojsf2.util.FacesUtil;
import com.algaworks.cursojsf2.util.HibernateUtil;
import com.algaworks.cursojsf2.util.Repositorios;

@FacesConverter(forClass=Pessoa.class)
public class PessoaConverter implements Converter {
	private Repositorios repositorios = new Repositorios();
	//busca na lista de pessoas com base no value = codigo da pessoa
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Pessoa retorno = null;
		
		if (value != null && !value.equals("")) {
			Pessoas pessoas = repositorios.getPessoas();
			retorno = pessoas.porCodigo(new Integer(value));
			if(retorno==null){
				String descricaoErro = "Pessoa não existe.";
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
			//return ((Pessoa) value).getCodigo().toString();
			Integer codigo =((Pessoa) value).getCodigo();
			return codigo == null  ? "":codigo.toString();
		}
		return null;
	}

}