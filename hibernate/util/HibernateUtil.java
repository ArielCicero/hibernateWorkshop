package hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

/**
 * HibernateUtil is a convenience class for obtaining the Hibernate SessionFactory instance,
 * which are used for building hibernate sessions.
 * SessionFactory is an interface used for building hibernate sessions.
 * Usually an application has a single SessionFactory.
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static String hibernateConfigFile = "hibernateH2DB.cfg.xml";
//  private static String hibernateConfigFile = "hibernateOnlineDB.cfg.xml";
//  private static String hibernateConfigFile = "hibernateMySqlDB.cfg.xml";

    /**
     * create session factory
     */
    private static void buildSessionFactory() {
        if (sessionFactory == null) {
            ServiceRegistry registry = null;
            try {
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().configure(hibernateConfigFile);
                registry = builder.build();
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
    }

    /**
     * get session factory
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory==null)  {
            buildSessionFactory();
        }
        return sessionFactory;
    }

    /**
     * close session factory
     */
    public static void closeSessionFactory() {
        if ((sessionFactory!=null) && (sessionFactory.isClosed()==false)) {
            sessionFactory.close();
        }
    }
}
