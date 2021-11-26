package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.User;
import com.epam.jwd.subscription.exception.EntityExtractionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.lang.String.join;

public class MethodUserDao extends CommonDao<User> implements UserDao {

    private static final Logger LOG = LogManager.getLogger(MethodUserDao.class);

    private static final String USER_TABLE_NAME = "user";// u join account a on a.id = u.acc_id "; +
//            "join role r on r.id = a.role_id";
    private static final String ID_FIELD_NAME = "id";
    private static final String FIRST_NAME_FIELD_NAME = "first_name";
    private static final String LAST_NAME_FIELD_NAME = "last_name";
    private static final String AGE_FIELD_NAME = "age";
    private static final String EMAIL_FIELD_NAME = "email";
    private static final String ACC_ID_FIELD_NAME = "acc_id";
    private static final String INSERT_INTO = "insert %s (%s) values (?, ?, ?, ?, ?)";
    private static final String COMMA = ", ";

    private static final List<String> FIELDS = Arrays.asList(
            ID_FIELD_NAME, FIRST_NAME_FIELD_NAME, LAST_NAME_FIELD_NAME,
            AGE_FIELD_NAME, EMAIL_FIELD_NAME, ACC_ID_FIELD_NAME
    );

    private static final List<String> FIELDS_FOR_INSERT = Arrays.asList(
            FIRST_NAME_FIELD_NAME, LAST_NAME_FIELD_NAME,
            AGE_FIELD_NAME, EMAIL_FIELD_NAME, ACC_ID_FIELD_NAME
    );

    private final String selectByEmailExpression;
    private final String insertSql;

    private MethodUserDao(ConnectionPool pool) {
        super(pool, LOG);
        this.selectByEmailExpression = format(SELECT_ALL_FROM, String.join(COMMA, getFields())) +
                getTableName() + SPACE + format(WHERE_FIELD, EMAIL_FIELD_NAME);
        this.insertSql = format(INSERT_INTO, getInsertTableName(), join(COMMA, getInsertFields()));
    }

    protected List<String> getInsertFields() {
        return FIELDS_FOR_INSERT;
    }

    protected String getInsertTableName() {
        return USER_TABLE_NAME;
    }

    @Override
    protected String getTableName() {
        return USER_TABLE_NAME;
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
    protected User extractResult(ResultSet rs) throws EntityExtractionFailedException {
        try {
            return new User(
                    rs.getLong(ID_FIELD_NAME),
                    rs.getString(FIRST_NAME_FIELD_NAME),
                    rs.getString(LAST_NAME_FIELD_NAME),
                    rs.getInt(AGE_FIELD_NAME),
                    rs.getString(EMAIL_FIELD_NAME),
                    rs.getLong(ACC_ID_FIELD_NAME));
        } catch (SQLException e) {
            LOG.error("sql exception occurred extracting user from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

    @Override
    protected void fillEntity(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setInt(3, user.getAge());
        statement.setString(4, user.getEmail());
        statement.setLong(5, user.getAccId());
    }

    @Override
    public Optional<User> readUserByEmail(String email) {
        try {
            return executePreparedForGenericEntity(selectByEmailExpression,
                    this::extractResultCatchingException,
                    st -> st.setString(1, email));
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    public User create(User entity) {
        try {
            final int rowsUpdated = executePreparedUpdate(insertSql, st -> fillEntity(st, entity));
            if (rowsUpdated > 0) {
//                read() //todo: read by unique param
                return null;
            }
            return null; //todo: throw exc
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    @Override
    public Optional<User> read(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    private static class Holder {
        public static final UserDao INSTANCE = new MethodUserDao(ConnectionPool.instance());
    }

    static UserDao getInstance() {
        return MethodUserDao.Holder.INSTANCE;
    }
}
