package ru.job4j.hibernate.model;

import javax.persistence.*;

@Entity
@Table(name = "model")
public class LazyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "mark")
    private LazyMark mark;

    public static LazyModel of(String name, LazyMark mark) {
        var model = new LazyModel();
        model.name = name;
        model.mark = mark;
        return model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LazyMark getMark() {
        return mark;
    }
}
