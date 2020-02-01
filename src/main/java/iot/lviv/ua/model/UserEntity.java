package iot.lviv.ua.model;

import iot.lviv.ua.model.Annotation.Column;
import iot.lviv.ua.model.Annotation.PrimaryKey;
import iot.lviv.ua.model.Annotation.Table;

@Table(name = "address")
public class UserEntity {
    @PrimaryKey
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;

    public UserEntity() {
    }

    public UserEntity(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s",
                id, name, surname);
    }
}
