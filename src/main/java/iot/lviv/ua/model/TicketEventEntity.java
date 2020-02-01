package iot.lviv.ua.model;

import java.math.BigDecimal;

import iot.lviv.ua.model.Annotation.Column;
import iot.lviv.ua.model.Annotation.PrimaryKey;
import iot.lviv.ua.model.Annotation.Table;

@Table(name = "address")
public class TicketEventEntity {
    @PrimaryKey
    @Column(name = "id")
    private int id;
    @Column(name = "number")
    private int number;
    @Column(name = "row")
    private int row;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "free")
    private boolean free;
    @Column(name = "place")
    private int place;
    @Column(name = "event_id")
    private int eventId;

    public TicketEventEntity() {
    }

    public TicketEventEntity(int id, int number, int row,
                             BigDecimal price, boolean free, int place,
                             int eventId) {
        this.id = id;
        this.number = number;
        this.row = row;
        this.price = price;
        this.free = free;
        this.place = place;
        this.eventId = eventId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s",
                id, number, row, price, free, place, eventId);
    }
}
