package iot.lviv.ua.DAO.implementation;

import iot.lviv.ua.model.metadata.ColumnMetaData;
import iot.lviv.ua.model.metadata.ForeignKeyMetaData;
import iot.lviv.ua.model.metadata.TableMetaData;
import iot.lviv.ua.persistant.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetaDataDAOImpl {

    public List<String> findAllTableName() throws SQLException {
        List<String> tableNames = new ArrayList<>();
        String[] types = {"TABLE"};

        Connection connection = ConnectionManager.getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet result = databaseMetaData.getTables(connection.getCatalog(), null, "%", types);

        while (result.next()) {
            String tableName = result.getString("TABLE_NAME");
            tableNames.add(tableName);
        }
        return tableNames;
    }

    public List<TableMetaData> getTablesStructure() throws SQLException {
        List<TableMetaData> tableMetaDataList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();

        String[] types = {"TABLE"};
        String dbName = connection.getCatalog();
        ResultSet result = databaseMetaData.getTables(dbName, null, "%", types);

        while (result.next()) {
            String tableName = result.getString("TABLE_NAME");
            TableMetaData tableMetaData = new TableMetaData();
            tableMetaData.setDBName(dbName);
            tableMetaData.setTableName(tableName);

            //GetPrimarykeys
            List<String> pkList = new ArrayList<>();
            ResultSet PKs = databaseMetaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
            while (PKs.next()) {
                pkList.add(PKs.getString("COLUMN_NAME"));
            }

            //GetColumnsForTable
            List<ColumnMetaData> columnsMetaData = new ArrayList<>();
            ResultSet columnsRS = databaseMetaData.getColumns(dbName, null, tableName, "%");
            while (columnsRS.next()) {
                ColumnMetaData columnMetaData = new ColumnMetaData();
                columnMetaData.setColumnName(columnsRS.getString("COLUMN_NAME"));
                columnMetaData.setDataType(columnsRS.getString("TYPE_NAME"));
                columnMetaData.setColumnSize(columnsRS.getString("COLUMN_SIZE"));
                columnMetaData.setDecimalDigits(columnsRS.getString("DECIMAL_DIGITS"));
                boolean cond = columnsRS.getString("IS_NULLABLE").equals("YES") ? true : false;
                columnMetaData.setNullable(cond);
                cond = columnsRS.getString("IS_AUTOINCREMENT").equals("IS_AUTOINCREMENT") ? true : false;
                columnMetaData.setAutoIncrement(cond);

                columnMetaData.setPrimaryKey(false);
                for (String pkName : pkList) {
                    if (columnMetaData.getColumnName().equals(pkName)) {
                        columnMetaData.setPrimaryKey(true);
                        break;
                    }
                }
                columnsMetaData.add(columnMetaData);
            }
            tableMetaData.setColumnMetaData(columnsMetaData);

            //Get Foreign Keys
            List<ForeignKeyMetaData> fkMetaDataList = new ArrayList<>();
            ResultSet FKsRS = databaseMetaData.getImportedKeys(dbName, null, tableName);
            while (FKsRS.next()) {
                ForeignKeyMetaData fk = new ForeignKeyMetaData();
                fk.setFkColumnName(FKsRS.getString("FKCOLUMN_NAME"));
                fk.setPkTableName(FKsRS.getString("PKTABLE_NAME"));
                fk.setPkColumnName(FKsRS.getString("PKCOLUMN_NAME"));
                fkMetaDataList.add(fk);
            }
            tableMetaData.setForeignKeyList(fkMetaDataList);

            tableMetaDataList.add(tableMetaData);
        }
        return tableMetaDataList;
    }
}