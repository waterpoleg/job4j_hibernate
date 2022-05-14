package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.Candidate;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of(1, "Alex", "newbie", 30);
            Candidate two = Candidate.of(2, "Nikolay", "senior", 100);
            Candidate three = Candidate.of(3, "Nikita", "middle", 50);

            session.save(one);
            session.save(two);
            session.save(three);

            var getAll = session.createQuery("from Candidate");
            for (Object candidate : getAll.list()) {
                System.out.println(candidate);
            }

            var getById = session.createQuery("from Candidate where id = :id")
                    .setParameter("id", 3);
            System.out.println(getById.uniqueResult());

            var getByName = session.createQuery("from Candidate where name = :name")
                    .setParameter("name", "Alex");
            for (Object candidate : getByName.list()) {
                System.out.println(candidate);
            }

            session.createQuery(
                    "update Candidate set name = :name, experience = :exp where id = :id")
                    .setParameter("name", "Nikolay II")
                    .setParameter("exp", "super senior")
                    .setParameter("id", 2)
                    .executeUpdate();
            var getUpdated = session.createQuery("from Candidate where id = :id")
                    .setParameter("id", 2);
            System.out.println(getUpdated.uniqueResult());

            session.createQuery("delete from Candidate where id = :id")
                    .setParameter("id", 3)
                    .executeUpdate();
            var getDeleted = session.createQuery("from Candidate where id = :id")
                    .setParameter("id", 3);
            if (getDeleted.uniqueResult() == null) {
                System.out.println("Successfully deleted");
            }

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
