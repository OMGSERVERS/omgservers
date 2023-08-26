package com.omgservers.base.operation.prepareShardSql;

import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;


@Slf4j
@QuarkusTest
class PrepareShardSqlOperationTest extends Assertions {

    @Inject
    PrepareShardSqlOperation prepareShardSqlOperation;

    @Test
    void whenPrepareShardSql() {
        String sql = """
                insert into $schema.tab_user (uuid, username, password)
                values ($1, $2, $3)
                """;
        String raw = """
                insert into shard_00010.tab_user (uuid, username, password)
                values ($1, $2, $3)
                """;
        String result = prepareShardSqlOperation.prepareShardSql(sql, 10);
        assertEquals(raw, result);
    }
}