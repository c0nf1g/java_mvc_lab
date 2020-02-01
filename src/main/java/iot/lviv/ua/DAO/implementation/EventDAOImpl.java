package iot.lviv.ua.DAO.implementation;

import iot.lviv.ua.DAO.EventDAO;
import iot.lviv.ua.model.EventEntity;
import iot.lviv.ua.persistant.ConnectionManager;
import iot.lviv.ua.transformer.Transformer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAOImpl implements EventDAO {
    private static final String FIND_ALL = "SELECT * FROM event";
    private static final String DELETE = "DELETE FROM event WHERE id=?";
    private static final String CREATE = "INSERT event (id, date, description," +
            " address_id, artist_id, event_type) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE event SET id=?, date=?, description=?, " +
            "address_id=?, artist_id=?, event_type=? WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM event WHERE id=?";

    @Override
    public List<EventEntity> findAll() throws SQLException {
        List<EventEntity> events = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
                while (resultSet.next()) {
                    events.add((EventEntity) new Transformer(EventEntity.class).fromResultSetToEntity(resultSet));
                }
            }
        }
        return events;
    }

    @Override
    public EventEntity findById(Integer id) throws SQLException {
        EventEntity entity=null;
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1,id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    entity=(EventEntity) new Transformer(EventEntity.class).fromResultSetToEntity(resultSet);
                    break;
                }
            }
        }
        return entity;
    }

    @Override
    public int create(EventEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setInt(1,entity.getId());
            ps.setString(2,entity.getDate());
            ps.setString(3,entity.getDescription());
            ps.setInt(4,entity.getAddressId());
            ps.setInt(5,entity.getArtistId());
            ps.setString(6,entity.getEventType());
            return ps.executeUpdate();
        }
    }

    @Override
    public int update(EventEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1,entity.getDate());
            ps.setString(2,entity.getDescription());
            ps.setInt(3,entity.getAddressId());
            ps.setInt(4,entity.getArtistId());
            ps.setString(5,entity.getEventType());
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
