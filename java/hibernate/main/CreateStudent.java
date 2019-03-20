package hibernate.main;

import hibernate.entity.Student;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

/**
 * Creating students for the Hibernate Workshop at the Software Development Academy
 */
public class CreateStudent {
    public static void main(String[] args) {
        List<String[]> data = Arrays.asList(new String[][] {
                // {firstName, lastName, email}
                { "Anna", "Petko", "ap@gmail.com" },
                { "Ariel", "Cicero", "ac@gmail.se" },
                { "Elvis", "Tanyi", "et@gmail.com" },
                { "Madina", "Artkova", "ma@gmail.com" },
                { "Sonakshi", "Guru", "sg@gmail.com" },
                { "Zeynep", "Dal", "zd@gmail.se" }
        });

        Student student = null;

        // create session
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // start a transaction
            session.beginTransaction();

            System.out.println(">> Creating and Saving the student...");

            for (String[] value : data) {
                String firstName = value[0];
                String lastName = value[1];
                String email = value[2];

                // create a student object
                student = new Student(firstName, lastName, email);

                // save the student object
                session.save(student);
            }

            // commit transaction
            session.getTransaction().commit();

            System.out.println(">> DONE!!");
        }
        finally {
            // close session
            HibernateUtil.closeSessionFactory();
        }
    }
}
