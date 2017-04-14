package com.algaworks.cursojsf2.financeiro.repository.infra;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.algaworks.cursojsf2.financeiro.repository.Lancamentos;
import com.algaworks.cursojsf2.financeiro.repository.Pessoas;

import util.io.Io;

public class PessoasHibernate implements Pessoas{
	private Session session;
	
	public PessoasHibernate(Session session){
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> todas() {
		Io.out("Hibernate retornou lista de pessoas");
		return session.createCriteria(Pessoa.class)
				.addOrder(Order.asc("nome"))
				.list();
	}

	@Override
	public Pessoa porCodigo(Integer codigo) {
		return (Pessoa) session.get(Pessoa.class, codigo);
	}

	@Override
	public void deletar(Pessoa pessoa) {
		session.delete(pessoa);
		Io.out("Hibernate deletou pessoa");
	}

	@Override
	public void guardar(Pessoa pessoa) {
		session.merge(pessoa);
		Io.out("Hibernate cadastrou pessoa");
	}
	
	@SuppressWarnings("unchecked")
	public List<Pessoa> todas(int limiteLinhasPorPagina, int pagina) {
		
		
		if(limiteLinhasPorPagina==0){
			limiteLinhasPorPagina=1;
			return null;
		}
		if(pagina==0){
			pagina=1;
			return null;
		} 
		
		
		//Long numLinhasTotalTabela = quantidadeDeRegistros();
		
		int linhaInicial=(pagina*limiteLinhasPorPagina)-limiteLinhasPorPagina;
		
		//int totalPaginas = (numLinhasTotalTabela.intValue()-linhaInicial)/limiteLinhasPorPagina;
		//int totalPaginas = totalRegistrosPorPagina(limiteLinhasPorPagina);
		//if((numLinhasTotalTabela.intValue()-linhaInicial)%limiteLinhasPorPagina!=0){
		//	Io.out("Divisão tem resto necessario adiconar mais uma pagina");
		//	Io.out("A ultima pagina terá menos linhas que as demais");
		//	totalPaginas=totalPaginas+1;
		//}
		
		Io.out("Hibernate retornou lista de pessoas com um limite de "+limiteLinhasPorPagina+" por pagina");
		Io.out("Exibindo Pagina "+pagina);
		Io.out("Linha inicial "+linhaInicial);
		//Io.out("Total de paginas "+totalPaginas);
		return session.createCriteria(Pessoa.class)
				.addOrder(Order.asc("nome"))
				.setFirstResult(linhaInicial)
				.setMaxResults(limiteLinhasPorPagina)
				.list();
		}
	
	/**
	 * Busca Pessoa pelo numero da linha, index retornando um resultado unico
	 */
	public Pessoa porNumLinha(int index) {
		return (Pessoa) session.createCriteria(Pessoa.class)
				.addOrder(Order.asc("nome"))
				.setFirstResult(index)
				.setMaxResults(1)
				.uniqueResult();
		}
	
	public Long quantidadeDeRegistros() {
		Long numLinhasTotalTabela = (Long) session
		.createCriteria(Pessoa.class)
		.setProjection(Projections.rowCount())
		.uniqueResult();
		Io.out("A Quantidade de registros na tabela Pessoas é de "+numLinhasTotalTabela);
		return numLinhasTotalTabela;
		
	}
	
	public int quantidadeDePaginas(int limiteLinhasPorPagina){
		Long numLinhasTotalTabela = quantidadeDeRegistros();
		int totalPaginas = (numLinhasTotalTabela.intValue())/limiteLinhasPorPagina;
		//int linhaInicial=(paginna*limiteLinhasPorPagina)-limiteLinhasPorPagina;
		//System.out.println(linhaInicial);
		//int totalPaginas = (numLinhasTotalTabela.intValue()-linhaInicial)/limiteLinhasPorPagina;
		//if((numLinhasTotalTabela.intValue()-linhaInicial)%limiteLinhasPorPagina!=0){
				//	Io.out("Divisão tem resto necessario adiconar mais uma pagina");
				//	Io.out("A ultima pagina terá menos linhas que as demais");
				//	totalPaginas=totalPaginas+1;
				//}
		
		
		
		if((numLinhasTotalTabela.intValue())%limiteLinhasPorPagina!=0){
			Io.out("Divisão tem resto necessario adiconar mais uma pagina");
			Io.out("A ultima pagina terá menos linhas que as demais");
			totalPaginas=totalPaginas+1;
		}
		return totalPaginas;
		
		}

	
	

}
