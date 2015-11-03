package edu.sjsu.cmpe275.lab2.Utility;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Nakul on 02-Nov-15.
 * Utility class to initiate hibernate session.
 */

public class HUtility {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /*
     * Singleton method get the hibernate session
     * returns the created SessionFactory object
     */
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration()
                    .configure(); // configures settings from hibernate.cfg.xml
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            // If you miss the below line then it will complain about a missing dialect setting
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /*
     * method that Closes caches and connection pools
     */
    public static void shutdown() {

        getSessionFactory().close();
    }
}
