package iot.lviv.ua.service;

import iot.lviv.ua.DAO.implementation.MetaDataDAOImpl;
import iot.lviv.ua.model.metadata.TableMetaData;

import java.sql.SQLException;
import java.util.List;

public class MetaDataService {
    public List<String> findAllTableName() throws SQLException {
        return new MetaDataDAOImpl().findAllTableName();
    }

    public List<TableMetaData> getTablesStructure() throws SQLException {
        return new MetaDataDAOImpl().getTablesStructure();
    }

}
