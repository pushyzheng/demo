package org.example.example.rws;

import org.apache.shardingsphere.api.hint.HintManager;
import org.example.example.rws.dao.tables.TUser;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@SuppressWarnings("resource")
@SpringBootTest(classes = ExampleReadWriteSplittingApplication.class)
@ExtendWith(SpringExtension.class)
class ExampleReadWriteSplittingApplicationTests {

    @Resource
    private DSLContext dslContext;

    /**
     * 插入到主库
     */
    @Test
    void insertToMaster() {
        int execute = dslContext.insertInto(TUser.T_USER)
                .set(TUser.T_USER.USERNAME, "Markfurt")
                .set(TUser.T_USER.PASSWORD, "123")
                .set(TUser.T_USER.GENDER, "male")
                .set(TUser.T_USER.TIMESTAMP, LocalDateTime.now())
                .execute();

        Assertions.assertEquals(1, execute);
    }

    /**
     * 查询从 '从库' 查
     */
    @Test
    void readFromSlave() {
        long id = 738526380391661569L;
        Result<Record> result = readById(id);
        System.out.println(result);
    }

    /**
     * 通过 {@link HintManager} 设置, 可以强制查主库
     */
    @Test
    void forceToReadFromMaster() {
        HintManager.getInstance().setMasterRouteOnly(); // 设置

        System.out.println(readById(738526382610448386L));
    }

    private Result<Record> readById(long id) {
        Result<Record> result = dslContext.select()
                .from(TUser.T_USER)
                .where(TUser.T_USER.ID.equal(id))
                .fetch();
        return result;
    }
}
