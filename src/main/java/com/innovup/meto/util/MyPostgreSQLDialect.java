package com.innovup.meto.util;

import org.hibernate.dialect.PostgreSQLDialect;

import java.sql.Types;

public class MyPostgreSQLDialect extends PostgreSQLDialect {
    public MyPostgreSQLDialect() {
        super();
        registerColumnType(Types.OTHER, "uuid");
    }
}
