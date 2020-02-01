package iot.lviv.ua.DAO.implementation;

import iot.lviv.ua.DAO.CredentialDAO;
import iot.lviv.ua.model.CredentialEntity;
import iot.lviv.ua.persistant.ConnectionManager;
import iot.lviv.ua.transformer.Transformer;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredentialDAOImpl implements CredentialDAO {
    private static final String FIND_ALL = "SELECT * FROM credential";
    private static final String DELETE = "DELETE FROM credential WHERE id=?";
    private static final String CREATE = "INSERT credential (id, password, login, telephone, user_id, email) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE credential SET password=?, login=?, telephone=?, user_id=?, email=? WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM credential WHERE id=?";

    @Override
    public List<CredentialEntity> findAll() throws SQLException {
        List<CredentialEntity> credentials = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
                while (resultSet.next()) {
                    credentials.add((CredentialEntity) new Transformer(CredentialEntity.class).fromResultSetToEntity(resultSet));
                }
            }
        }
        return credentials;
    }


    @Override
    public CredentialEntity findById(Integer id) throws SQLException {
        CredentialEntity entity=null;
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1,id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    entity=(CredentialEntity) new Transformer(CredentialEntity.class).fromResultSetToEntity(resultSet);
                    break;
                }
            }
        }
        return entity;
    }

    @Override
    public int create(CredentialEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setInt(1,entity.getId());
            ps.setString(2,entity.getPassword());
            ps.setString(3,entity.getLogin());
            ps.setString(4,entity.getTelephone());
            ps.setInt(5,entity.getUserId());
            ps.setString(6,entity.getEmail());
            return ps.executeUpdate();
        }
    }

    @Override
    public int update(CredentialEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1,entity.getPassword());
            ps.setString(2,entity.getLogin());
            ps.setString(3,entity.getTelephone());
            ps.setInt(4,entity.getUserId());
            ps.setString(5,entity.getEmail());
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
