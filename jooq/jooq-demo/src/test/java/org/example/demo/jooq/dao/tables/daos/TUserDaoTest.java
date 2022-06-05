package org.example.demo.jooq.dao.tables.daos;

import org.example.demo.jooq.dao.tables.TUser;
import org.example.demo.jooq.mock.UserFindOneByIdMockProvider;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author zuqin.zheng
 */
class TUserDaoTest {

    /**
     * Gets the mock DSL Context
     */
    public DSLContext getContext() {
        MockConnection connection = new MockConnection(new UserFindOneByIdMockProvider());
        return DSL.using(connection, SQLDialect.MYSQL);
    }

    @Test
    void fetchOneById() {
        long id = 1;

        Record result = getContext()
                .select()
                .from(TUser.T_USER)
                .where(TUser.T_USER.ID.equal(id))
                .fetchOne();

        Assertions.assertNotNull(result);
    }
}
