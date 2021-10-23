package com.epam.jwd.subscription.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementPreparator {

    void accept(PreparedStatement preparedStatement) throws SQLException;

}
