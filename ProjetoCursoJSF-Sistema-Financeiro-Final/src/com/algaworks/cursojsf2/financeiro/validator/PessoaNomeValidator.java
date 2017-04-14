package com.algaworks.cursojsf2.financeiro.validator;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.sun.faces.util.MessageFactory;

import util.io.Io;
import util.string.FormatarString;

@FacesValidator("com.algaworks.PessoaNome")
public class PessoaNomeValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String nome = (String) value;
		Io.out("Validando pessoa "+nome);
		if (nome != null) {
			Io.out("A pessoa "+nome);
			nome=(FormatarString.removerEspacosDuplicados(nome));
			Io.out("teve seu nome alteado para: "+nome+" espaços em excesso foram removidos");
			if (nome.isEmpty() || nome.equalsIgnoreCase(" ") || nome.equalsIgnoreCase("  +")){
				Object label = MessageFactory.getLabel(context, component);
				
				String descricaoErro = label + " O nome da Pessoa não pode estar vazio.";
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						descricaoErro, descricaoErro);
				throw new ValidatorException(message);
			}
			
		}
	}

}