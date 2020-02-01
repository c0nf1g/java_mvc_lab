package iot.lviv.ua.DAO.implementation;

import iot.lviv.ua.DAO.ArtistDAO;
import iot.lviv.ua.model.ArtistEntity;
import iot.lviv.ua.persistant.ConnectionManager;
import iot.lviv.ua.transformer.Transformer;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAOImpl implements ArtistDAO {
    private static final String FIND_ALL = "SELECT * FROM artist";
    private static final String DELETE = "DELETE FROM artist WHERE id=?";
    private static final String CREATE = "INSERT artist (id, name, surname, nickname) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE artist SET name=?, surname=?, nickname=? WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM artist WHERE id=?";
    
    @Override
    public List<ArtistEntity> findAll() throws SQLException {
        List<ArtistEntity> artists = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
                while (resultSet.next()) {
                    artists.add((ArtistEntity) new Transformer(ArtistEntity.class).fromResultSetToEntity(resultSet));
                }
            }
        }
        return artists;
    }

    @Override
    public ArtistEntity findById(Integer id) throws SQLException {
        ArtistEntity entity=null;
        Connection connection = ConnectionManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1,id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    entity=(ArtistEntity) new Transformer(ArtistEntity.class).fromResultSetToEntity(resultSet);
                    break;
                }
            }
        }
        return entity;
    }

    @Override
    public int create(ArtistEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setInt(1,entity.getId());
            ps.setString(2,entity.getName());
            ps.setString(3,entity.getSurname());
            ps.setString(4,entity.getNickname());
            return ps.executeUpdate();
        }
    }

    @Override
    public int update(ArtistEntity entity) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1,entity.getName());
            ps.setString(2,entity.getSurname());
            ps.setString(3,entity.getNickname());
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
