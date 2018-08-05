package hibenate;
import org.hibernate.SessionFactory;

import java.io.File;

import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory buildSessionFactory() {

		// Create the SessionFactory from hibernate.cfg.xml
		Configuration configuration = new Configuration();
		configuration.configure(new File("C:\\Users\\user\\Desktop\\EEWorkspace\\Kinema\\src\\hibernate.cfg.xml"));
		System.out.println("Hibernate Configuration loaded");

		@SuppressWarnings("deprecation")
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		return sessionFactory;

	}

	public static SessionFactory getSessionFactory() {
		return buildSessionFactory();
	}

}