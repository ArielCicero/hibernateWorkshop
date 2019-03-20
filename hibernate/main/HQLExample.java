package hibernate.main;

import hibernate.entity.Student;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Hibernate Query Language (HQL) example for the Hibernate Workshop at the Software Development Academy
 * Deleting all the rows in the DB
 */
public class HQLExample {
    public static void main(String[] args) {
        // create session
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try {
            // get transaction
            transaction = session.getTransaction();

            // create query
            String hql = "delete from Student";
            Query query = session.createQuery(hql);

            // update database (even if we are deleting files the action realised in the DB it's
            // an update of its status
            query.executeUpdate();

            // commit the transaction in successful scenario
            transaction.commit();

            System.out.println(">> DONE!!");
        }catch(Exception e){
            System.out.println(">> ERROR!!");
            System.out.println(e.getMessage());
            // cancel all changes and revert back the transaction to its state before commit
            transaction.rollback();
        }
        finally {
            // close session
            HibernateUtil.closeSessionFactory();
        }
    }
}
