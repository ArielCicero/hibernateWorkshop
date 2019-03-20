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
 * Updating students for the Hibernate Workshop at the Software Development Academy
 */
public class UpdateStudent {
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

            System.out.println(">> Getting student with id 5: ");
            // get the list of students that fulfill the criteria
            Student student = query.uniqueResult();

            // print the student
            System.out.println(student);

            // update the student
            System.out.println(">> updating the student... ");
            student.setFirstName("Sona");
            student.setEmail("sona@gmail.se");
            session.update(student);

            // commit changes
            transaction.commit();

            // print the student updated
            System.out.println(">> Student updated: ");
            System.out.println(student);

            System.out.println(">> DONE!!");
        }catch(Throwable t){
            System.out.println(">> ERROR!!");
            System.out.println(t.getLocalizedMessage());
        }
        finally {
            // close session
            HibernateUtil.closeSessionFactory();
        }
    }
}
