package com.yuanwhy.simple.sharding.datasource;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.clause.MySqlSelectIntoStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by yuanwhy on 17/3/28.
 */
public class LogicStatement implements Statement {

    private LogicConnection logicConnection;

    private ResultSet resultSet;

    public LogicStatement(LogicConnection logicConnection) {
        this.logicConnection = logicConnection;
    }


    public LogicConnection getLogicConnection() {
        return logicConnection;
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return 0;
    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {

    }

    @Override
    public int getMaxRows() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {

    }

    @Override
    public void cancel() throws SQLException {

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public void setCursorName(String name) throws SQLException {

    }

    @Override
    public boolean execute(String sql) throws SQLException {

        List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);

        SQLStatement currentSqlStatement = sqlStatements.get(0);
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        currentSqlStatement.accept(visitor);

        LogicDataSource logicDataSource = logicConnection.getLogicDataSource();

        Object fieldValueForDb = 0, fieldValueForTable = 0;

        if (currentSqlStatement instanceof MySqlInsertStatement) {

            // TODO: 17/4/4
            List<SQLExpr> columns = ((MySqlInsertStatement) currentSqlStatement).getColumns();
            List<SQLExpr> columnValues = ((MySqlInsertStatement) currentSqlStatement).getValues().getValues();
            for (int i = 0; i < columns.size(); i++) {

                String columnName = ((SQLIdentifierExpr) columns.get(i)).getName();

                if (columnName.equals(logicDataSource.getShardingRule().getFieldNameForDb())) {

                    fieldValueForDb = ((SQLValuableExpr) columnValues.get(i)).getValue();

                }

                if (columnName.equals(logicDataSource.getShardingRule().getFieldNameForTable())) {

                    fieldValueForTable = ((SQLValuableExpr) columnValues.get(i)).getValue();

                }

            }

        } else {

            List<TableStat.Condition> conditions = visitor.getConditions();


            for (TableStat.Condition condition : conditions) {
                if (condition.getColumn().getName().equals(logicDataSource.getShardingRule().getFieldNameForDb())) {
                    fieldValueForDb = condition.getValues().get(0);
                }

                if (condition.getColumn().getName().equals(logicDataSource.getShardingRule().getFieldNameForTable())) {
                    fieldValueForTable = condition.getValues().get(0);
                }
            }


        }
        resultSet = doExecute(sql, visitor.getCurrentTable(), logicDataSource, fieldValueForDb, fieldValueForTable);

        return true;


    }

    private ResultSet doExecute(String originalSql, String logicTableName, LogicDataSource logicDataSource, Object fieldValueForDb, Object fieldValueForTable) throws SQLException {

        String physicalDbName = logicDataSource.getLogicDatabase() + logicDataSource.getShardingRule().getDbSuffix(fieldValueForDb);
        String physicalTableName = logicTableName + logicDataSource.getShardingRule().getTableSuffix(fieldValueForTable);

        DataSource physicalDataSource = logicDataSource.getPhysicalDataSourceMap().get(physicalDbName);


        String executableSql = originalSql.replaceAll(logicDataSource.getLogicDatabase(), physicalDbName).replaceAll(logicTableName, physicalTableName);


        Connection physicalConnection = physicalDataSource.getConnection();
        Statement physicalStatement = physicalConnection.createStatement();
        physicalStatement.execute(executableSql);

        return physicalStatement.getResultSet();

    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return resultSet;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {

    }

    @Override
    public int getFetchDirection() throws SQLException {
        return 0;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {

    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        return 0;
    }

    @Override
    public void addBatch(String sql) throws SQLException {

    }

    @Override
    public void clearBatch() throws SQLException {

    }

    @Override
    public int[] executeBatch() throws SQLException {
        return new int[0];
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }


    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
