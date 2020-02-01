package iot.lviv.ua.DAO.implementation;

import iot.lviv.ua.DAO.PassageDAO;

import java.sql.SQLException;
import java.util.List;

import iot.lviv.ua.model.PassageEntity;
import iot.lviv.ua.persistant.ConnectionManager;
import iot.lviv.ua.transformer.Transformer;

import java.sql.*;
import java.util.ArrayList;

public class PassageDAOImpl implements PassageDAO {
    private static final String FIND_ALL = "SELECT * FROM passage";
    private static final String DELETE = "DELETE FROM passage WHERE id=?";
    private static final String CREATE = "INSERT passage (id, arrive_city, departure_city, arrive_time, " +
            "departure_time, number, passage_type, company) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE passage SET arrive_city=?, departure_city=?, arrive_time=?, " +
            "departure_time=?, number=?, passage_type=?, company=? WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM passage WHERE id=?";

    @Override
    public List<PassageEntity> findAll() throws SQLException {
        List<PassageEntity> passages = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
                while (resultSet.next()) {
                    passages.add((PassageEntity) new Transformer(PassageEntity.class).fromResultSetToEntity(resultSet));
                }
            }
        }
        return passages;
    }

    @Override
    public PassageEntity findById(Integer id) throws SQLException {
        PassageEntity entity=null;
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1,id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    entity=(PassageEntity) new Transformer(PassageEntity.class).fromResultSetToEntity(resultSet);
                    break;
                }
            }
        }
        return entity;
    }

    @Override
    public int create(PassageEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setInt(1,entity.getId());
            ps.setString(2,entity.getArrivalCity());
            ps.setString(3,entity.getDepartureCity());
            ps.setString(4,entity.getArrivalTime());
            ps.setString(5,entity.getDepartureTime());
            ps.setInt(6,entity.getNumber());
            ps.setString(7,entity.getPassageType());
            ps.setString(8,entity.getCompany());
            return ps.executeUpdate();
        }
    }

    @Override
    public int update(PassageEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1,entity.getArrivalCity());
            ps.setString(2,entity.getDepartureCity());
            ps.setString(3,entity.getArrivalTime());
            ps.setString(4,entity.getDepartureTime());
            ps.setInt(5,entity.getNumber());
            ps.setString(6,entity.getPassageType());
            ps.setString(7,entity.getCompany());
            ps.setInt(8,entity.getId());
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
