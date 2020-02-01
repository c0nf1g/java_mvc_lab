package iot.lviv.ua.DAO.implementation;

import iot.lviv.ua.DAO.AddressDAO;
import iot.lviv.ua.model.AddressEntity;
import iot.lviv.ua.persistant.ConnectionManager;
import iot.lviv.ua.transformer.Transformer;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAOImpl implements AddressDAO {
    private static final String FIND_ALL = "SELECT * FROM address";
    private static final String DELETE = "DELETE FROM address WHERE id=?";
    private static final String CREATE = "INSERT address (id, city, street, number) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE address SET city=?, street=?, number=? WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM address WHERE id=?";

    @Override
    public List<AddressEntity> findAll() throws SQLException {
        List<AddressEntity> addresses = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
                while (resultSet.next()) {
                    addresses.add((AddressEntity) new Transformer(AddressEntity.class).fromResultSetToEntity(resultSet));
                }
            }
        }
        return addresses;
    }

    @Override
    public AddressEntity findById(Integer id) throws SQLException {
        AddressEntity entity=null;
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1,id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    entity=(AddressEntity) new Transformer(AddressEntity.class).fromResultSetToEntity(resultSet);
                    break;
                }
            }
        }
        return entity;
    }

    @Override
    public int create(AddressEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setInt(1,entity.getId());
            ps.setString(2,entity.getCity());
            ps.setString(3,entity.getStreet());
            ps.setInt(4,entity.getNumber());
            return ps.executeUpdate();
        }
    }

    @Override
    public int update(AddressEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1,entity.getCity());
            ps.setString(2,entity.getStreet());
            ps.setInt(3,entity.getNumber());
            ps.setInt(4,entity.getId());
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
