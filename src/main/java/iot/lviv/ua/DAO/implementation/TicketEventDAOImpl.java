package iot.lviv.ua.DAO.implementation;

import iot.lviv.ua.DAO.TicketEventDAO;
import iot.lviv.ua.model.TicketEventEntity;
import iot.lviv.ua.persistant.ConnectionManager;
import iot.lviv.ua.transformer.Transformer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketEventDAOImpl implements TicketEventDAO {
    private static final String FIND_ALL = "SELECT * FROM ticket_event";
    private static final String DELETE = "DELETE FROM ticket_event WHERE id=?";
    private static final String CREATE = "INSERT ticket_event (id, number, row, price, " +
            "free, place, event_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE ticket_event SET number=?, row=?, price=?," +
            " free=?, place=?, event_id=? WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM ticket_event WHERE id=?";

    @Override
    public List<TicketEventEntity> findAll() throws SQLException {
        List<TicketEventEntity> eventTickets = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
                while (resultSet.next()) {
                    eventTickets.add((TicketEventEntity) new Transformer(TicketEventEntity.class).fromResultSetToEntity(resultSet));
                }
            }
        }
        return eventTickets;
    }

    @Override
    public TicketEventEntity findById(Integer id) throws SQLException {
        TicketEventEntity entity=null;
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1,id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    entity=(TicketEventEntity) new Transformer(TicketEventEntity.class).fromResultSetToEntity(resultSet);
                    break;
                }
            }
        }
        return entity;
    }

    @Override
    public int create(TicketEventEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setInt(1,entity.getId());
            ps.setInt(2,entity.getNumber());
            ps.setInt(3,entity.getRow());
            ps.setBigDecimal(4,entity.getPrice());
            ps.setBoolean(5,entity.isFree());
            ps.setInt(6,entity.getPlace());
            ps.setInt(7,entity.getEventId());
            return ps.executeUpdate();
        }
    }

    @Override
    public int update(TicketEventEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setInt(1,entity.getNumber());
            ps.setInt(2,entity.getRow());
            ps.setBigDecimal(3,entity.getPrice());
            ps.setBoolean(4,entity.isFree());
            ps.setInt(5,entity.getPlace());
            ps.setInt(6,entity.getEventId());
            ps.setInt(7,entity.getId());
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
