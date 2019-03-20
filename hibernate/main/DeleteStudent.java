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
 * Deleting students for the Hibernate Workshop at the Software Development Academy
 */
public class DeleteStudent {
    public static void main(String[] args) {
        // create session
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // get transaction
            Transaction transaction = session.getTransaction();

            // begin transaction
            transaction.begin();

            // create criteria
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> criteria = builder.createQuery(Student.class);

            // create root
            Root<Student> root = criteria.from(Student.class);

            // create query to retrieve student with id = 5
            criteria.select(root);
            criteria.where(builder.equal(root.get("id"), 5));
            Query<Student> query = session.createQuery(criteria);

            System.out.println(">> Getting student with id 5... ");
            // get the list of students that fulfill the criteria
            Student student = query.uniqueResult();

            // delete the student
            System.out.println(">> Deleting student with id 5... ");
            session.delete(student);

            // commit changes
            transaction.commit();

            // print the student deleted
            System.out.println(">> Student deleted: ");
            System.out.println(student);

            System.out.println(">> DONE!!");
        }catch(Exception e){
            System.out.println(">> ERROR!!");
            System.out.println(e.getMessage());
        }
        finally {
            // close session
            HibernateUtil.closeSessionFactory();
        }
    }
}
