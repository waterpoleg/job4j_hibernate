package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.CarMark;
import ru.job4j.hibernate.model.CarModel;

public class HbmCarsRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
             Session session = sf.openSession()
        ) {
            session.beginTransaction();
            var one = CarModel.of("model1");
            session.save(one);
            var two = CarModel.of("model2");
            session.save(two);
            var three = CarModel.of("model3");
            session.save(three);
            var four = CarModel.of("model4");
            session.save(four);
            var five = CarModel.of("model5");
            session.save(five);
            var mark = CarMark.of("mark");
            mark.addModel(session.load(CarModel.class, 1));
            mark.addModel(session.load(CarModel.class, 2));
            mark.addModel(session.load(CarModel.class, 3));
            mark.addModel(session.load(CarModel.class, 4));
            mark.addModel(session.load(CarModel.class, 5));
            session.save(mark);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
