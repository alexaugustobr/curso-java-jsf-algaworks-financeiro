package com.algaworks.cursojsf2.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.io.Io;
import util.io.IoTipoMensagem;


@WebFilter(servletNames={"Faces Servlet"})
public class HibernateSessionFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		//session.close();
		Io.out("Desruiu o filtro HibernateFilter");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Io.out("Aplicou o filtro HibernateSession");
		Session session = HibernateUtil.getSessition();
		Transaction trx = null;
		try {
			trx = session.beginTransaction();
			request.setAttribute("session", session);
			
			chain.doFilter(request, response);
			
			trx.commit();
		} catch(Exception  e) {
			if (trx != null){
				trx.rollback();
			}
		} finally {
			session.close();
		}
		
		 
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		Io.out("Inicializou o filtro HibernateSession");
		 
	}
	
}
