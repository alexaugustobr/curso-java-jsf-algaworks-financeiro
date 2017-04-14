package com.algaworks.cursojsf2.util;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	
	static {
		try {
		Configuration configuration = new Configuration();
		configuration.configure();
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).buildServiceRegistry();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	//retorna uma conexão com o banco de dados e cria um session com uma connection do jdbc
	public static Session getSessition(){
		return sessionFactory.openSession();
	}
		
}
