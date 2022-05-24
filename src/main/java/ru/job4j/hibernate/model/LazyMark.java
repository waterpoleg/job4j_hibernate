package ru.job4j.hibernate.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mark")
public class LazyMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "mark")
    private List<LazyModel> models = new ArrayList<>();

    public static LazyMark of(String name) {
        var carMark = new LazyMark();
        carMark.name = name;
        return carMark;
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

    public void addModel(LazyModel model) {
        this.models.add(model);
    }

    public List<LazyModel> getModels() {
        return models;
    }
}
