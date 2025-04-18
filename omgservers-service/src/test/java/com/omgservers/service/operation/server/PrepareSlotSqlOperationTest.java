package com.omgservers.service.operation.server;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class PrepareSlotSqlOperationTest extends BaseTestClass {

    @Inject
    PrepareSlotSqlOperation prepareSlotSqlOperation;

    @Test
    void givenSql_whenExecute_thenPrepared() {
        final var sql = "select id from $slot.tab_table";
        final var preparedSql = prepareSlotSqlOperation.execute(sql, 123);
        assertEquals("select id from slot_123.tab_table", preparedSql);
    }
}