package iot.lviv.ua.DAO.implementation;

import iot.lviv.ua.DAO.OrderPassageDAO;
import iot.lviv.ua.model.OrderPassageEntity;
import iot.lviv.ua.persistant.ConnectionManager;
import iot.lviv.ua.transformer.Transformer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderPassageDAOImpl implements OrderPassageDAO {
    private static final String FIND_ALL = "SELECT * FROM order_passage";
    private static final String DELETE = "DELETE FROM order_passage WHERE id=?";
    private static final String CREATE = "INSERT order_passage (id, payed, user_id, " +
            "delivery, payment_method, passage_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE order_passage SET payed=?, user_id=?, " +
            "delivery=?, payment_method=?, passage_id=? WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM order_passage WHERE id=?";

    @Override
    public List<OrderPassageEntity> findAll() throws SQLException {
        List<OrderPassageEntity> event_orders = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
                while (resultSet.next()) {
                    event_orders.add((OrderPassageEntity) new Transformer(OrderPassageEntity.class).fromResultSetToEntity(resultSet));
                }
            }
        }
        return event_orders;
    }

    @Override
    public OrderPassageEntity findById(Integer id) throws SQLException {
        OrderPassageEntity entity=null;
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1,id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    entity=(OrderPassageEntity) new Transformer(OrderPassageEntity.class).fromResultSetToEntity(resultSet);
                    break;
                }
            }
        }
        return entity;
    }

    @Override
    public int create(OrderPassageEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setBoolean(1,entity.isPayed());
            ps.setInt(2,entity.getUserId());
            ps.setString(3,entity.getDelivery());
            ps.setString(4,entity.getPaymentMethod());
            ps.setInt(5,entity.getPassageId());
            ps.setInt(6,entity.getId());
            return ps.executeUpdate();
        }
    }

    @Override
    public int update(OrderPassageEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setInt(1,entity.getId());
            ps.setBoolean(2,entity.isPayed());
            ps.setInt(3,entity.getUserId());
            ps.setString(4,entity.getDelivery());
            ps.setString(4,entity.getPaymentMethod());
            ps.setInt(4,entity.getPassageId());
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
