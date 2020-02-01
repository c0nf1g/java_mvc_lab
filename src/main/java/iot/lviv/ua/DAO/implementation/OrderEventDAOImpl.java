package iot.lviv.ua.DAO.implementation;

import iot.lviv.ua.DAO.OrderEventDAO;

import java.sql.SQLException;
import java.util.List;

import iot.lviv.ua.model.OrderEventEntity;
import iot.lviv.ua.persistant.ConnectionManager;
import iot.lviv.ua.transformer.Transformer;

import java.sql.*;
import java.util.ArrayList;

public class OrderEventDAOImpl implements OrderEventDAO {
    private static final String FIND_ALL = "SELECT * FROM order_event";
    private static final String DELETE = "DELETE FROM order_event WHERE id=?";
    private static final String CREATE = "INSERT order_event (id, payed, user_id, " +
            "delivery, payment_method, event_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE order_event SET payed=?, user_id=?, " +
            "delivery=?, payment_method=?, event_id=? WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM order_event WHERE id=?";

    @Override
    public List<OrderEventEntity> findAll() throws SQLException {
        List<OrderEventEntity> event_orders = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
                while (resultSet.next()) {
                    event_orders.add((OrderEventEntity) new Transformer(OrderEventEntity.class).fromResultSetToEntity(resultSet));
                }
            }
        }
        return event_orders;
    }

    @Override
    public OrderEventEntity findById(Integer id) throws SQLException {
        OrderEventEntity entity=null;
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1,id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    entity=(OrderEventEntity) new Transformer(OrderEventEntity.class).fromResultSetToEntity(resultSet);
                    break;
                }
            }
        }
        return entity;
    }

    @Override
    public int create(OrderEventEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setInt(1,entity.getId());
            ps.setBoolean(2,entity.isPayed());
            ps.setInt(3,entity.getUserId());
            ps.setString(4,entity.getDelivery());
            ps.setString(4,entity.getPaymentMethod());
            ps.setInt(4,entity.getEventId());
            return ps.executeUpdate();
        }
    }

    @Override
    public int update(OrderEventEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setBoolean(1,entity.isPayed());
            ps.setInt(2,entity.getUserId());
            ps.setString(3,entity.getDelivery());
            ps.setString(4,entity.getPaymentMethod());
            ps.setInt(5,entity.getEventId());
            ps.setInt(6,entity.getId());
            return ps.executeUpdate();
        }
    }

    @Override
    public int delete(Integer id) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1,id);
            return ps.executeUpdate();
        }
    }
}
