package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.db.ResultSetExtractor;
import com.epam.jwd.subscription.db.StatementPreparator;
import com.epam.jwd.subscription.entity.Entity;
import com.epam.jwd.subscription.exception.EntityExtractionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.lang.String.join;

public abstract class CommonDao<T extends Entity> implements EntityDao<T> {

    private static final String INSERT_INTO = "insert into %s (%s)";
    private static final String ID_FIELD_NAME = "id";
    private static final String COMMA = ", ";

    private static final Logger LOG = LogManager.getLogger(CommonDao.class);
    protected static final String SELECT_ALL_FROM = "select * from ";
    protected static final String WHERE_FIELD = "where %s = ?";
    protected static final String SPACE = " ";

    protected final ConnectionPool pool;
    private final String selectAllExpression;
    private final String selectByIdExpression;
//    private final String insertSql;
    private final Logger logger;

    protected CommonDao(ConnectionPool pool, Logger logger) {
        this.pool = pool;
        this.logger = logger;
        selectAllExpression = SELECT_ALL_FROM + getTableName();
        this.selectByIdExpression = selectAllExpression + SPACE + format(WHERE_FIELD, ID_FIELD_NAME);
//        this.insertSql = format(INSERT_INTO, getTableName(), join(COMMA, getFields()));
    }

    @Override
    public List<T> read() {
        try {
            return executeStatement(selectAllExpression, this::extractResultCatchingException);
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<T> read(Long id) {
        try {
            return executePreparedForGenericEntity(selectByIdExpression,
                    this::extractResultCatchingException,
                    st -> st.setLong(1, id));
        } catch (InterruptedException e) {
            logger.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    private List<T> executeStatement(String sql, ResultSetExtractor<T> extractor) throws InterruptedException {
        try (final Connection connection = pool.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(sql)) {
            return extractor.extractAll(resultSet);
        } catch (SQLException e) {
            LOG.error("SQL exception occurred", e);
            LOG.debug("SQL: {}", sql);
        } catch (EntityExtractionFailedException e) {
            LOG.error("Could not extract entity", e);
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            throw e;
        }
        return Collections.emptyList();
    }

    private List<T> executePreparededForEntities (String sql,
                                       ResultSetExtractor<T> extractor,
                                       StatementPreparator statementPreparation) throws InterruptedException {
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement statement = connection.prepareStatement(sql)) {
            if (statementPreparation != null) {
                statementPreparation.accept(statement);
            }
            final ResultSet resultSet = statement.executeQuery();
            return extractor.extractAll(resultSet);
        } catch (SQLException e) {
            LOG.error("SQL exception occured", e);
            LOG.debug("SQL: {}", sql);
        } catch (EntityExtractionFailedException e) {
            LOG.error("Could not extract entity", e);
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            throw e;
        }
        return Collections.emptyList();
    }

    protected T extractResultCatchingException(ResultSet rs) throws EntityExtractionFailedException {
        try {
            return extractResult(rs);
        } catch (SQLException e) {
            logger.error("sql exception occurred extracting entity from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

    protected <G> Optional<G> executePreparedForGenericEntity(String sql, ResultSetExtractor<G> extractor,
                                                              StatementPreparator statementPreparation) throws InterruptedException {
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement statement = connection.prepareStatement(sql)) {
            if (statementPreparation != null) {
                statementPreparation.accept(statement);
            }
            final ResultSet resultSet = statement.executeQuery();
            return resultSet.next()
                    ? Optional.ofNullable(extractor.extract(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            logger.error("sql exception occurred", e);
            logger.debug("sql: {}", sql);
        } catch (EntityExtractionFailedException e) {
            logger.error("could not extract entity", e);
        } catch (InterruptedException e) {
            logger.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            throw e;
        }
        return Optional.empty();
    }

    protected abstract String getTableName();

    protected abstract List<String> getFields();

    protected abstract String getIdFieldName();

    protected abstract T extractResult(ResultSet rs) throws SQLException, EntityExtractionFailedException;
}
