
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.algaworks.cursojsf2.financeiro.repository.Pessoas;
import com.algaworks.cursojsf2.financeiro.service.GestaoLancamentos;
import com.algaworks.cursojsf2.financeiro.service.GestaoPessoas;
import com.algaworks.cursojsf2.util.HibernateUtil;
import com.algaworks.cursojsf2.util.Repositorios;

public class TesteHibernate2 {
	;
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Repositorios repositorios = new Repositorios();
		Pessoa pessoa = new Pessoa();
		Session session = HibernateUtil.getSessition();
		Pessoas pessoaRepositorio = repositorios.getPessoas(session);
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		GestaoPessoas gestaoPessoa = new GestaoPessoas(repositorios.getPessoas(session), repositorios.getLancamentos(session));
		// recebendo o numero de linhas da tabela
		// exemplo
		int numLinhasTotalTabela = pessoaRepositorio.quantidadeDeRegistros().intValue();
		
		
		
		pessoas = pessoaRepositorio.todas();
		for (Pessoa p : pessoas){
			
			System.out.println("Pessoa: "+p.getNome());
			
		}
		
		try {
			TimeUnit.MILLISECONDS.sleep(400);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Listando pessoas: ");
		for (int i=0;i<numLinhasTotalTabela;i++){
			pessoa=pessoaRepositorio.porNumLinha(i);
			System.out.println("Pessoa: "+pessoa.getNome());
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

		System.exit(0);

	}
}
