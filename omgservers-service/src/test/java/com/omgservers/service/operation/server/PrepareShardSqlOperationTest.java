package com.omgservers.service.operation.server;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class PrepareShardSqlOperationTest extends BaseTestClass {

    @Inject
    PrepareShardSqlOperation prepareShardSqlOperation;

    @Test
    void givenSql_whenPrepareShardSql_thenPrepared() {
        final var sql = "select id from $schema.tab_table";
        final var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, 123);
        assertEquals("select id from shard_00123.tab_table", preparedSql);
    }
}