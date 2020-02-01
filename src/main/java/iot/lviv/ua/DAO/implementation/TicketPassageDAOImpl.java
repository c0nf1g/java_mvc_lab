package iot.lviv.ua.DAO.implementation;

import iot.lviv.ua.DAO.TicketPassageDAO;
import iot.lviv.ua.model.TicketPassageEntity;
import iot.lviv.ua.persistant.ConnectionManager;
import iot.lviv.ua.transformer.Transformer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketPassageDAOImpl implements TicketPassageDAO {
    private static final String FIND_ALL = "SELECT * FROM ticket_passage";
    private static final String DELETE = "DELETE FROM ticket_passage WHERE id=?";
    private static final String CREATE = "INSERT ticket_passage (id, row, number, free, " +
            "price, class, passage_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE ticket_passage SET row=?, number=?, free=?," +
            " price=?, class=?, passage_id=? WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM ticket_passage WHERE id=?";

    @Override
    public List<TicketPassageEntity> findAll() throws SQLException {
        List<TicketPassageEntity> passageTickets = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
                while (resultSet.next()) {
                    passageTickets.add((TicketPassageEntity) new Transformer(TicketPassageEntity.class).fromResultSetToEntity(resultSet));
                }
            }
        }
        return passageTickets;
    }

    @Override
    public TicketPassageEntity findById(Integer id) throws SQLException {
        TicketPassageEntity entity=null;
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1,id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    entity=(TicketPassageEntity) new Transformer(TicketPassageEntity.class).fromResultSetToEntity(resultSet);
                    break;
                }
            }
        }
        return entity;
    }

    @Override
    public int create(TicketPassageEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setInt(1,entity.getId());
            ps.setInt(2,entity.getRow());
            ps.setInt(3,entity.getNumber());
            ps.setBoolean(4,entity.isFree());
            ps.setBigDecimal(5,entity.getPrice());
            ps.setString(6,entity.getPassageClass());
            ps.setInt(7,entity.getPassageId());
            return ps.executeUpdate();
        }
    }

    @Override
    public int update(TicketPassageEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setInt(1,entity.getRow());
            ps.setInt(2,entity.getNumber());
            ps.setBoolean(3,entity.isFree());
            ps.setBigDecimal(4,entity.getPrice());
            ps.setString(5,entity.getPassageClass());
            ps.setInt(6,entity.getPassageId());
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
