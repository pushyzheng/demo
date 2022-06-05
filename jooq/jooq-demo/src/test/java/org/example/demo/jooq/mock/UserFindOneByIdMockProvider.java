package org.example.demo.jooq.mock;

import org.example.demo.jooq.dao.tables.TUser;
import org.example.demo.jooq.dao.tables.records.TUserRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @author zuqin.zheng
 */
public class UserFindOneByIdMockProvider implements MockDataProvider {

    @Override
    public MockResult[] execute(MockExecuteContext ctx) throws SQLException {
        DSLContext create = DSL.using(SQLDialect.MYSQL);
        MockResult[] mock = new MockResult[1];

        TUserRecord tUserRecord = new TUserRecord();
        tUserRecord.setId(1L);
        tUserRecord.setUsername("Jasonfurt");
        tUserRecord.setPassword("123");
        tUserRecord.setGender("1");
        tUserRecord.setTimestamp(LocalDateTime.now());

        Result<TUserRecord> result = create.newResult(TUser.T_USER);
        result.add(tUserRecord);

        mock[0] = new MockResult(1, result);
        return mock;
    }
}
