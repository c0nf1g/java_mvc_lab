package iot.lviv.ua.DAO.implementation;

import iot.lviv.ua.DAO.UserDAO;
import iot.lviv.ua.model.UserEntity;
import iot.lviv.ua.persistant.ConnectionManager;
import iot.lviv.ua.transformer.Transformer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final String FIND_ALL = "SELECT * FROM user";
    private static final String DELETE = "DELETE FROM user WHERE id=?";
    private static final String CREATE = "INSERT user (id, name, surname) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE user SET name=?, surname=? WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM user WHERE id=?";
    
    @Override
    public List<UserEntity> findAll() throws SQLException {
        List<UserEntity> classes = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
                while (resultSet.next()) {
                    classes.add((UserEntity) new Transformer(UserEntity.class).fromResultSetToEntity(resultSet));
                }
            }
        }
        return classes;
    }

    @Override
    public UserEntity findById(Integer id) throws SQLException {
        UserEntity entity=null;
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1,id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    entity=(UserEntity) new Transformer(UserEntity.class).fromResultSetToEntity(resultSet);
                    break;
                }
            }
        }
        return entity;
    }

    @Override
    public int create(UserEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setInt(1,entity.getId());
            ps.setString(2,entity.getName());
            ps.setString(3,entity.getSurname());
            return ps.executeUpdate();
        }
    }

    @Override
    public int update(UserEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1,entity.getName());
            ps.setString(2,entity.getSurname());
            ps.setInt(3,entity.getId());
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
