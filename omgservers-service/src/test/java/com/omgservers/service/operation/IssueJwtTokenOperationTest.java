package com.omgservers.service.operation;

import com.omgservers.service.operation.prepareShardSql.PrepareShardSqlOperation;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;


@Slf4j
@QuarkusTest
class IssueJwtTokenOperationTest extends Assertions {

    @Inject
    PrepareShardSqlOperation prepareShardSqlOperation;

    @Test
    void whenIssueUserJwtToken() {
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