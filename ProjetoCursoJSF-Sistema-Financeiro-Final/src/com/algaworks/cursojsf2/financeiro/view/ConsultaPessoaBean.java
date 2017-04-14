package com.algaworks.cursojsf2.financeiro.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.algaworks.cursojsf2.financeiro.model.Lancamento;
import com.algaworks.cursojsf2.financeiro.model.Pessoa;
import com.algaworks.cursojsf2.financeiro.repository.DBException;
import com.algaworks.cursojsf2.financeiro.repository.Lancamentos;
import com.algaworks.cursojsf2.financeiro.repository.Pessoas;
import com.algaworks.cursojsf2.financeiro.service.GestaoLancamentos;
import com.algaworks.cursojsf2.financeiro.service.GestaoPessoas;
import com.algaworks.cursojsf2.financeiro.service.RegraNegocioException;
import com.algaworks.cursojsf2.util.FacesUtil;
import com.algaworks.cursojsf2.util.HibernateUtil;
import com.algaworks.cursojsf2.util.Repositorios;

import util.io.Io;

@ManagedBean
@ViewScoped
public class ConsultaPessoaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
	private Pessoa pessoaSelecionada;
	private Repositorios repositorios = new Repositorios();
	private int limiteLinhasPorPagina = 15;
	private int pagina = 1;
	private int totalPaginas;

	//Executa via f:event da pagina
	public void fEventOnLoad(ComponentSystemEvent event) {
		this.carregarTabela();
	}

	//Utilizado pelo input text, faz o redirect na pagina
	public void numPaginaModificada(ValueChangeEvent event) {
		if (event.getNewValue() == null) {
			this.pagina = 1;
		} else {
			this.pagina = (Integer.parseInt(event.getNewValue().toString()));
		}

		Io.out("Numero da pagina modificada " + pagina);
		// passa os atributos alterados para o componente
		// FacesContext.getCurrentInstance().renderResponse();
		FacesContext fc = FacesContext.getCurrentInstance();
		ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
		// retorno de pagina via nav, gerenciador de navegacao
		Io.out("Redirecionando pagina");
		nav.performNavigation("ConsultaPessoa?pagina=" + pagina + "&faces-redirect=true");
		//EVENT ONLOAD ja regarrega a pagina
	}

	public void carregarTabela() {

		// pessoaSelecionada=null;
		// this.listaPessoas=null;
		// this.pagina=this.pagina+1;
		Pessoas pessoasDAO = this.repositorios.getPessoas();
		totalPaginas = pessoasDAO.quantidadeDePaginas(limiteLinhasPorPagina);
		Io.out("Carregou total de paginas " + totalPaginas);
		if (totalPaginas > 0) {
			if (pagina <= 0) {
				pagina = 1;
				FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR,
						"Está pagina não existe a primeria pagina do relatório é " + pagina,
						"Está pagina não existe a primeria pagina do relatório é " + pagina);
				this.listaPessoas = pessoasDAO.todas(limiteLinhasPorPagina, pagina);
			} else if (pagina > totalPaginas) {
				pagina = totalPaginas;
				FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR,
						"Está pagina não existe a ultima pagina do relatório é " + totalPaginas,
						"Está pagina não existe a ultima pagina do relatório é " + totalPaginas);
				//carrega a ultima pagina
				this.listaPessoas = pessoasDAO.todas(limiteLinhasPorPagina, totalPaginas);
			} else {
				Io.out("Carregando tabela");
				this.listaPessoas = pessoasDAO.todas(limiteLinhasPorPagina, pagina);

			}
		} else {
			//clear pois assim a tabela desaparece se não viver mais pessoas
			listaPessoas.clear();
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR,
					"Não a Pessoas para serem exibidas, favor cadastre uma pessoa!",
					"Não a Pessoas para serem exibidas, favor cadastre uma pessoa!");
		}

		// utilizado com o immetiate
		// agora desnecessario pois a pagina carrega com o event ONLOAD
		// FacesContext.getCurrentInstance().renderResponse();

	}

	public void paginaExiste(int pagina) {
		Pessoas pessoasDAO = this.repositorios.getPessoas();
		totalPaginas = pessoasDAO.quantidadeDePaginas(limiteLinhasPorPagina);
		Io.out("Carregou total de paginas " + totalPaginas);
		if (totalPaginas > 0) {
			if (pagina <= 0) {
				FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR,
						"Está pagina não existe a primeria pagina do relatório é 1",
						"Está pagina não existe a primeria pagina do relatório é 1");

			} else if (pagina > totalPaginas) {
				FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR,
						"Está pagina não existe a ultima pagina do relatório é " + totalPaginas,
						"Está pagina não existe a ultima pagina do relatório é " + totalPaginas);
			} else {
				this.pagina = pagina;
				// DESATIVADO pois EVENT ONLOAD ja regarrega a pagina
				// this.carregarTabela();
			}
		} else {
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR,
					"Não a Pessoas para serem exibidas, favor cadastre uma pessoa!",
					"Não a Pessoas para serem exibidas, favor cadastre uma pessoa!");
		}

	}

	public void paginaExisteComRedirect(int pagina) {
		Pessoas pessoasDAO = this.repositorios.getPessoas();
		totalPaginas = pessoasDAO.quantidadeDePaginas(limiteLinhasPorPagina);
		Io.out("Carregou total de paginas " + totalPaginas);
		if (totalPaginas > 0) {
			if (pagina <= 0) {
				FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR,
						"Está pagina não existe a primeria pagina do relatório é 1",
						"Está pagina não existe a primeria pagina do relatório é 1");

			} else if (pagina > totalPaginas) {
				FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR,
						"Está pagina não existe a ultima pagina do relatório é " + totalPaginas,
						"Está pagina não existe a ultima pagina do relatório é " + totalPaginas);
			} else {
				// this.pagina=pagina;
				FacesContext fc = FacesContext.getCurrentInstance();
				ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication()
						.getNavigationHandler();
				// redireciona a pagina e o event onload recarrega
				nav.performNavigation("ConsultaPessoa?pagina=" + pagina + "&faces-redirect=true");
			}
		} else {
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR,
					"Não a Pessoas para serem exibidas, favor cadastre uma pessoa!",
					"Não a Pessoas para serem exibidas, favor cadastre uma pessoa!");
		}

	}

	public String paginaProxima() {
		this.pagina = this.pagina + 1;
		Io.out("PAGINA Proxima link " + pagina);
		// faces-redirect=true
		return "ConsultaPessoa?pagina=" + pagina + "&faces-redirect=true";
	}

	public String paginaAnterior() {
		this.pagina = this.pagina - 1;
		if (pagina == 0) {
			pagina = 1;
		}
		Io.out("PAGINA Anterior link " + pagina);
		// faces-redirect=true
		return "ConsultaPessoa?pagina=" + pagina + "&faces-redirect=true";
	}

	public void btnProximaPagina() {
		int novaPagina = this.pagina + 1;
		Io.out("PAGINA Proxima btn " + novaPagina);
		paginaExiste(novaPagina);
	}

	public void btnPaginaAnterior() {
		int novaPagina = this.pagina - 1;
		Io.out("PAGINA Anterior btn " + novaPagina);
		paginaExiste(novaPagina);
	}

	public void btnProximaPaginaRedirect() {
		int novaPagina = this.pagina + 1;
		Io.out("PAGINA Proxima btn redirect " + novaPagina);
		paginaExisteComRedirect(novaPagina);
	}

	public void btnPaginaAnteriorRirect() {
		int novaPagina = this.pagina - 1;
		Io.out("PAGINA Anterior btn redirect" + novaPagina);
		paginaExisteComRedirect(novaPagina);
	}

	public int getLimiteLinhasPorPagina() {
		return limiteLinhasPorPagina;
	}

	public void setLimiteLinhasPorPagina(int limiteLinhasPorPagina) {
		this.limiteLinhasPorPagina = limiteLinhasPorPagina;
	}

	public Pessoa getPessoaSelecionada() {
		return pessoaSelecionada;
	}

	public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
		this.pessoaSelecionada = pessoaSelecionada;
	}

	public void excluir() {
		Io.out("Excluindo pessoa" + pessoaSelecionada.getNome());

		//Pessoas pessoasDAO = this.repositorios.getPessoas();
		//pessoasDAO.deletar(pessoaSelecionada);
		
		GestaoPessoas gestaoPessoas =new GestaoPessoas(
						this.repositorios.getPessoas(),
						this.repositorios.getLancamentos()		
						);
		//
		
		try {
			gestaoPessoas.excluir(this.pessoaSelecionada);
			// this.inicializar();
			// Se não passou pela excessão ele cai nessa mensagem
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Excluido a Pessoa!", "Excluido a Pessoa!");
		} catch (RegraNegocioException e) {
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
		} catch (DBException e) {
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
		}
		
		
	}

	public List<Pessoa> getPessoas() {
		return listaPessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.listaPessoas = pessoas;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		Io.out("SET PAGINA " + pagina);
		this.pagina = pagina;
	}
}