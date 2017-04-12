package com.yuanwhy.simple.sharding.jdbc;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by yuanwhy on 17/3/28.
 */
public class LogicConnection implements Connection {

    private final LogicDataSource logicDataSource;

    private Connection physicalConnection;

    private String physicalDbName;

    private boolean autoCommit = true;

    public LogicConnection(LogicDataSource logicDataSource) {
        this.logicDataSource = logicDataSource;
    }


    public LogicDataSource getLogicDataSource() {
        return logicDataSource;
    }

    public Connection getPhysicalConnection() {
        return physicalConnection;
    }

    public void setPhysicalConnection(Connection physicalConnection) {
        this.physicalConnection = physicalConnection;
    }

    public String getPhysicalDbName() {
        return physicalDbName;
    }

    public void setPhysicalDbName(String physicalDbName) {
        this.physicalDbName = physicalDbName;
    }

    @Override
    public Statement createStatement() throws SQLException {

        Statement statement = new  LogicStatement(this);

        return statement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {

        LogicPreparedStatement prepareStatement = new LogicPreparedStatement(this, sql);

        return prepareStatement;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return null;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return null;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.autoCommit = autoCommit;
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return autoCommit;
    }

    /**
     * commit之后为了能开启新的不同的分库的事务,所以清空之前的物理连接
     * @throws SQLException
     */
    @Override
    public void commit() throws SQLException {

        physicalConnection.commit();

        resetPhysicalProperties();

    }

    /**
     * 和commit做法保持一致
     * @throws SQLException
     */
    @Override
    public void rollback() throws SQLException {

        physicalConnection.rollback();

        resetPhysicalProperties();

    }

    /**
     * 关闭逻辑连接的含义: 关闭物理连接同时清空, 使得逻辑连接可以开启新的不同分库的物理连接
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {

        if (physicalConnection != null) {
            resetPhysicalProperties();
        }

    }

    @Override
    public boolean isClosed() throws SQLException {

        if (physicalConnection == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {

    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {

    }

    @Override
    public String getCatalog() throws SQLException {
        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {

    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return null;
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {

    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {

    }

    @Override
    public String getSchema() throws SQLException {
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }


    private void resetPhysicalProperties() throws SQLException {
        this.autoCommit = true;
        this.physicalConnection.close();
        this.physicalConnection = null;
        this.physicalDbName = null;
    }

}
