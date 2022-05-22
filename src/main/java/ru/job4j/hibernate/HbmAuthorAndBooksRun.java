package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.Author;
import ru.job4j.hibernate.model.Book;

public class HbmAuthorAndBooksRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book one = Book.of("book 1");
            Book two = Book.of("book 2");
            Book third = Book.of("book 3");

            Author first = Author.of("Nikolay");
            first.getBooks().add(one);
            first.getBooks().add(two);
            first.getBooks().add(third);
            session.persist(first);

            Author second = Author.of("Anatoliy");
            second.getBooks().add(two);
            session.persist(second);

            session.remove(second);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
