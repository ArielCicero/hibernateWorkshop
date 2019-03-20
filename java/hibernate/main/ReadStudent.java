package hibernate.main;

import hibernate.entity.Student;
import hibernate.util.HibernateUtil;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Reading students for the Hibernate Workshop at the Software Development Academy
 */
public class ReadStudent {
    public static void main(String[] args) {

        // create session
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // create criteria
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> criteria = builder.createQuery(Student.class);

            // create root
            Root<Student> root = criteria.from(Student.class);

            // create query to retrieve all the students
            criteria.select(root);
            Query<Student> query = session.createQuery(criteria);

            // get the list of students that fulfill the criteria
            List<Student> studentList = query.getResultList();

            // print the list of students
            System.out.println("########## STUDENTS ########");
            for (Student student : studentList) {
                System.out.println(student);
            }
            System.out.println("############################");

            // retrieve student based on one id: primary key
            System.out.println(">> Getting student with id 1: ");

            Student student = session.get(Student.class, 1);
            System.out.println(student);

            System.out.println("############################");
            System.out.println("############################");

            // retrieve student/s with first name "Anna"
            // create query
            criteria.select(root);
            criteria.where(builder.equal(root.get("firstName"),"Ariel"));
            query = session.createQuery(criteria);

            System.out.println(">> Getting students with first name \"Ariel\": ");
            try {
                // get the list of students that fulfill the criteria for one result
                student = query.getSingleResult();

                // print the student
                System.out.println(student);
            }catch(NonUniqueResultException e){
                // get the list of students that fulfill the criteria for various result
                studentList = query.getResultList();

                // print the list of students
                for (Student s : studentList) {
                    System.out.println(s);
                }
            }catch(NoResultException e2){
                System.out.println(e2.getMessage());
            }


            System.out.println("############################");
            System.out.println("############################");

            // retrieve student/s with email ending like "gmail.se"
            // create query
            criteria.select(root);
            criteria.where(builder.like(root.get("email"),"%gmail.se"));
            query = session.createQuery(criteria);

            // get the list of students that fulfill the criteria
            System.out.println(">> Getting student with email ending like \"gmail.se\": ");
            studentList = query.getResultList();

            // print the list of students
            for (Student s : studentList) {
                System.out.println(s);
            }

            System.out.println("############################");
            System.out.println();

            System.out.println(">> DONE!!");
        }
        finally {
            // close session
            HibernateUtil.closeSessionFactory();
        }
    }
}
