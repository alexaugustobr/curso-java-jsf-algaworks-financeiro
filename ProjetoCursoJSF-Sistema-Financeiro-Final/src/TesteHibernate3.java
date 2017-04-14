
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.algaworks.cursojsf2.util.HibernateUtil;

public class TesteHibernate3 {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessition();
		
		

		
		List<Pessoa> pessoas = session.createCriteria(Pessoa.class).list();
		Pessoa pessoaunica = (Pessoa)session.get(Pessoa.class, 7);
		if(pessoaunica==null){
			System.out.println("Pessoa é null");
		} else {
			System.out.println("Pessoa é: "+pessoaunica.getNome());
		}
		
		
		for (Pessoa p : pessoas) {
			System.out.println(p.getCodigo() + " - " + p.getNome());
		}
		System.out.println("\n\n");
		// seleciona apenas pessoas com codigo maior q 3, clausula where greater than
		// pessoas = session
		// .createCriteria(Pessoa.class)
		// .add(Restrictions.gt("codigo", 3))
		// .list();
		// for (Pessoa p : pessoas){
		// System.out.println(p.getCodigo()+" - "+p.getNome());
		// }

		pessoas = session.createCriteria(Pessoa.class).list();
		for (Pessoa p : pessoas) {
			System.out.println(p.getCodigo() + " - " + p.getNome());

			List<Lancamento> lancamentos = session.createCriteria(Lancamento.class)
					.add(Restrictions.eq("pessoa.codigo", p.getCodigo()))
					.list();

			if ((lancamentos == null)) {
				System.out.println("Lançamentos é nulo");
			} else {
				if (lancamentos.isEmpty()) {
					System.out.println("Não a lançamentos para a pessoa de codigo: " + p.getCodigo());
				} else {
					System.out.println("Lançamentos");
					for (Lancamento l : lancamentos) {
						System.out.println("Codigo do Lançamento: " + l.getCodigo() + " - " + l.getDescricao());
					}
					lancamentos.clear();

				}
			}

			System.out.println("\n\n");
		}

		session.close();
		System.exit(0);

	}
}
