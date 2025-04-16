package com.omgservers.service.operation.server;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PrepareSqlOperationTest extends BaseTestClass {

    @Inject
    PrepareSqlOperationImpl prepareServerSqlOperation;

    @Test
    void givenMasterSql_whenExecute_thenPrepared() {
        final var sql = "select id from $master.tab_table";
        final var preparedSql = prepareServerSqlOperation.execute(sql);
        assertEquals("select id from master_15.tab_table", preparedSql);
    }

    @Test
    void givenServerSql_whenExecute_thenPrepared() {
        final var sql = "select id from $server.tab_table";
        final var preparedSql = prepareServerSqlOperation.execute(sql);
        assertEquals("select id from server_15.tab_table", preparedSql);
    }
}