package com.omgservers.service.operation.server;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PrepareServerSqlOperationTest extends BaseTestClass {

    @Inject
    PrepareServerSqlOperationImpl prepareServerSqlOperation;

    @Test
    void givenSql_whenExecute_thenPrepared() {
        final var sql = "select id from $server.tab_table";
        final var preparedSql = prepareServerSqlOperation.execute(sql);
        assertEquals("select id from server_15.tab_table", preparedSql);
    }
}