package iot.lviv.ua.model;

import iot.lviv.ua.model.Annotation.Column;
import iot.lviv.ua.model.Annotation.PrimaryKey;
import iot.lviv.ua.model.Annotation.Table;

@Table(name = "artist")
public class ArtistEntity {
    @PrimaryKey
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "nickname")
    private String nickname;

    public ArtistEntity() {

    }

    public ArtistEntity(int id, String name, String surname, String nickname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", id, name, surname, nickname);
    }
}
