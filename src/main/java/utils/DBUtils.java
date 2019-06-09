package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    static final private StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate_cfg.xml").build();
    static final private SessionFactory sessionFactory = new MetadataSources(DBUtils.registry).buildMetadata().buildSessionFactory();


    static public void insert(Object object) {
        Session session = sessionFactory.openSession();

        try (session) {
            Transaction tx = session.beginTransaction();
            session.persist(object);
            tx.commit();
        } catch (Exception e) {
            // log
        }
    }

    static public void saveOrUpdate(Object object) {
        Session session = sessionFactory.openSession();

        try (session) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(object);
            tx.commit();
        } catch (Exception e) {
            // log
        }
    }

    static public void delete(Object object) {
        Session session = sessionFactory.openSession();

        try (session) {
            Transaction tx = session.beginTransaction();
            session.delete(object);
            tx.commit();
        } catch (Exception e) {
            // log
        }
    }

    static public ArrayList selectAll(Class table) {
        Session session = sessionFactory.openSession();
        String hql = "FROM " + table.getName();
        Query query = session.createQuery(hql);
        List result = query.list();
        session.close();
        return (ArrayList) result;
    }

    static public ArrayList query(String sql) {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery(sql);
        List result = query.list();
        session.close();
        return (ArrayList) result;
    }

}
