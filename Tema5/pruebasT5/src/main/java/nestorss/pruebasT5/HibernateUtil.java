package nestorss.pruebasT5;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.metamodel.MetadataSources;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;

	protected void setUp() throws Exception {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // por defecto: hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}
	
	public static SessionFactory getSessionFactory() {
		  return sessionFactory;
	}
	
}
