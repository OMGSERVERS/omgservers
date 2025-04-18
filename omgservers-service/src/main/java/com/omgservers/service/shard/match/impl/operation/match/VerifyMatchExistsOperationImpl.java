package com.omgservers.service.shard.match.impl.operation.match;

import com.omgservers.service.operation.server.VerifyObjectExistsOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class VerifyMatchExistsOperationImpl implements VerifyMatchExistsOperation {

    final VerifyObjectExistsOperation verifyObjectExistsOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int slot,
                                final Long id) {
        return verifyObjectExistsOperation.execute(
                sqlConnection,
                slot,
                """
                        select id
                        from $slot.tab_match
                        where id = $1 and deleted = false
                        limit 1
                        """,
                List.of(id),
                "Match");
    }
}
