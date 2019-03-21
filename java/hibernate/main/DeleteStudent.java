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
        int id = 5;

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

            // create query to retrieve student with a specific ID
            criteria.select(root);
            criteria.where(builder.equal(root.get("id"), id));
            Query<Student> query = session.createQuery(criteria);

            System.out.println(">> Getting student with id "+ id +": ");
            // get the list of students that fulfill the criteria
            Student student = query.uniqueResult();
            if(student != null) {
                // delete the student
                System.out.println(">> Deleting student with id "+ id +": ");
                session.delete(student);

                // commit changes
                transaction.commit();

                // print the student deleted
                System.out.println(">> Student deleted: ");
                System.out.println(student);

                System.out.println(">> DONE!!");
            }
            else{
                System.out.println(">> THERE IS NO RECORD FOR THE ID NUMBER " + id);
            }
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
