
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.algaworks.cursojsf2.financeiro.repository.Lancamentos;
import com.algaworks.cursojsf2.util.HibernateUtil;

public class TesteHibernate {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		Session session = HibernateUtil.getSessition();

		// recebendo o numero de linhas da tabela
		// exemplo
		Long numLinhasTotalTabela = (Long) session
				.createCriteria(Lancamento.class)
				.setProjection(Projections.rowCount())
				.uniqueResult();
		
		System.out.println("Lancamentos");
		System.out.println("\n\nO Tamanho da tabela Lancamento é de: " + numLinhasTotalTabela.intValue()+" linhas");
		System.out.println("Linhas de registro percorrem de 0 a " + (numLinhasTotalTabela.intValue() - 1));

		int limiteLinhasPorPagina = 2;
		System.out.println("\nOs primeiros " + limiteLinhasPorPagina + " registros da tabela lacamento são:");
		List<Lancamento> lancamentos = session
				.createCriteria(Lancamento.class)
				.setFirstResult(0)
				.setMaxResults(limiteLinhasPorPagina)
				.list();
		
		int linhaInicial = 0;
		for (Lancamento l : lancamentos) {
			System.out.println("Linha: " + linhaInicial + " - Codigo: " + l.getCodigo() + " - Descricao: " + l.getDescricao());
			linhaInicial++;
		}

		
		
		
		limiteLinhasPorPagina = 2;
		linhaInicial = numLinhasTotalTabela.intValue() - limiteLinhasPorPagina;
		System.out.println("\nOs ultimos " + limiteLinhasPorPagina + " registros da tabela lacamento são:");
		lancamentos = session
				.createCriteria(Lancamento.class)
				.setFirstResult(linhaInicial)
				.setMaxResults(limiteLinhasPorPagina)
				.list();
		for (Lancamento l : lancamentos) {
			System.out.println("Linha: " + linhaInicial + " - Codigo: " + l.getCodigo() + " - Descricao: " + l.getDescricao());
			linhaInicial++;
		}

		
		
		
		//limite = 1;
		linhaInicial = (numLinhasTotalTabela.intValue()-1);//-1 pois é total-1 igual a ultima linha
		System.out.println("\nO ultimo registro da tabela lacamento é:");
		lancamentos = session
				.createCriteria(Lancamento.class)
				.setFirstResult((linhaInicial))
				.list();
		for (Lancamento l : lancamentos) {
			System.out.println("Linha: " + linhaInicial + " - Codigo: " + l.getCodigo() + " - Descricao: " + l.getDescricao());
		}
		
		
		
		linhaInicial = 0;
		System.out.println("\n\n\nTodo os registros: ");
		lancamentos = session
				.createCriteria(Lancamento.class)
				.list();
		for (Lancamento l : lancamentos) {
			System.out.println("Linha: " + linhaInicial + " - Codigo: " + l.getCodigo() + " - Descricao: " + l.getDescricao());
			linhaInicial++;
		}
		
		
		
		//numero de linhas totais da tabela
		System.out.println("\n\n\nPessoas");
		numLinhasTotalTabela = (Long) session
				.createCriteria(Pessoa.class)
				.setProjection(Projections.rowCount())
				.uniqueResult();
		
		System.out.println("\n\nO Tamanho da tabela Pessoas é de: " + numLinhasTotalTabela.intValue()+" linhas");
		System.out.println("Linhas de registro percorrem de 0 a " + (numLinhasTotalTabela.intValue() - 1));
		
		linhaInicial=3400;//linha inicial
		limiteLinhasPorPagina=2;//linhas por pagina
		int pagina=0;//pagina inicial sempre 0, pq não foi exibida nenhuma pagina antes de iniciar o codigo
		//int totalPaginas=4;//total final
		
		System.out.println("Exibindo "+limiteLinhasPorPagina+" linhas por pagina \nlinha de registro inical é: "+linhaInicial);
		
		int totalPaginas = (numLinhasTotalTabela.intValue()-linhaInicial)/limiteLinhasPorPagina;
		//se tiver resto é necessario adicionar uma pagina a mais
		if((numLinhasTotalTabela.intValue()-linhaInicial)%limiteLinhasPorPagina!=0){
			System.out.println("Divisão tem resto necessario adiconar mais uma pagina");
			System.out.println("A ultima pagina terá menos linhas que as demais");
			totalPaginas=totalPaginas+1;
		}
		System.out.println("\nTotal de paginas: " +totalPaginas);
		
		while (linhaInicial<numLinhasTotalTabela){
			List<Pessoa> pessoas = session
				.createCriteria(Pessoa.class)
				.setFirstResult(linhaInicial)
				.setMaxResults(limiteLinhasPorPagina)
				.list();
		if (pessoas.isEmpty()||pessoas==null){
			System.out.println("Não a mais resultados na pagina");
		} else {
			pagina++;
			System.out.println("\nPagina "+pagina);
			for (Pessoa p : pessoas) {
				System.out.println("Linha: " + linhaInicial + " - Codigo: " + p.getCodigo() + " - Nome: " + p.getNome());
				linhaInicial++;
				}
			System.out.println("Fim da pagina");
			}
		}
		
		
		System.out.println("\nNão a mais resultados");
		
		
		
		

		// seleciona apenas pessoas com codigo maior q 3, clausula where greater
		// than
		// pessoas = session
		// .createCriteria(Pessoa.class)
		// .add(Restrictions.gt("codigo", 3))
		// .list();
		// for (Pessoa p : pessoas){
		// System.out.println(p.getCodigo()+" - "+p.getNome());
		// }

		session.close();
		System.exit(0);

	}
}
