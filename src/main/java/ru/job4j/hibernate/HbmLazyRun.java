package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.LazyMark;
import ru.job4j.hibernate.model.LazyModel;

import java.util.List;

public class HbmLazyRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            LazyMark car = LazyMark.of("mark 1");
            session.save(car);
            LazyMark car2 = LazyMark.of("mark 2");
            session.save(car2);

            LazyModel one = LazyModel.of("model 1", car);
            session.save(one);
            LazyModel two = LazyModel.of("model 2", car);
            session.save(two);
            LazyModel three = LazyModel.of("model 3", car);
            session.save(three);
            LazyModel four = LazyModel.of("model 1", car2);
            session.save(four);

            List<LazyMark> marks = session.createQuery("from LazyMark").list();
            for (LazyMark mark : marks) {
                for (LazyModel model : mark.getModels()) {
                    System.out.println(model);
                }
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
