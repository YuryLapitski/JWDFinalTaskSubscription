package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.entity.Role;
import com.epam.jwd.subscription.exception.EntityExtractionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class MethodAccountDao extends CommonDao<Account> implements AccountDao {

    private static final Logger LOG = LogManager.getLogger(MethodAccountDao.class);

    private static final String ACCOUNT_TABLE_NAME = "account a join role r on r.id = a.role_id";
    private static final String ID_FIELD_NAME = "id";
    private static final String LOGIN_FIELD_NAME = "login";
    private static final String PASSWORD_FIELD_NAME = "a_pass";
    private static final String ROLE_FIELD_NAME = "r.role_name";

    private static final List<String> FIELDS = Arrays.asList(
            ID_FIELD_NAME, LOGIN_FIELD_NAME,
            PASSWORD_FIELD_NAME, ROLE_FIELD_NAME
    );

    private final String selectByLoginExpression;

    private MethodAccountDao(ConnectionPool pool) {
        super(pool, LOG);
        this.selectByLoginExpression = SELECT_ALL_FROM + getTableName() + SPACE + format(WHERE_FIELD, LOGIN_FIELD_NAME);
//        this.selectByLoginExpression = format(SELECT_ALL_FROM, String.join(", ", getFields()))
//                + getTableName() + SPACE + format(WHERE_FIELD, LOGIN_FIELD_NAME);
    }

    private static class Holder {
        public static final AccountDao INSTANCE = new MethodAccountDao(ConnectionPool.instance());
    }

    static AccountDao getInstance() {
        return MethodAccountDao.Holder.INSTANCE;
    }

    @Override
    public Optional<Account> readAccountByLogin(String login) {
        try {
            return executePreparedForGenericEntity(selectByLoginExpression,
                    this::extractResultCatchingException,
                    st -> st.setString(1, login));
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    protected String getTableName() {
        return ACCOUNT_TABLE_NAME;
    }

    @Override
    protected List<String> getFields() {
        return FIELDS;
    }

    @Override
    protected String getIdFieldName() {
        return ID_FIELD_NAME;
    }

    @Override
    protected Account extractResult(ResultSet rs) throws EntityExtractionFailedException {
        try {
            return new Account(
                    rs.getLong(ID_FIELD_NAME),
                    rs.getString(LOGIN_FIELD_NAME),
                    rs.getString(PASSWORD_FIELD_NAME),
                    Role.of(rs.getString(ROLE_FIELD_NAME)));
        } catch (SQLException e) {
            LOG.error("sql exception occured extracting edition from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

    @Override
    public Account create(Account entity) {
        return null;
    }

    @Override
    public Optional<Account> read(Long id) {
        return Optional.empty();
    }

    @Override
    public Account update(Account entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
