package com.omgservers.module.system.impl.operation.upsertLog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.log.LogModel;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertLogOperationImpl implements UpsertLogOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertLog(final ChangeContext<?> changeContext,
                                  final SqlConnection sqlConnection,
                                  final LogModel logModel) {
        return changeObjectOperation.changeObject(
                        changeContext, sqlConnection, 0,
                        """
                                insert into system.tab_log(id, created, message)
                                values($1, $2, $3)
                                on conflict (id) do
                                nothing
                                """,
                        Arrays.asList(
                                logModel.getId(),
                                logModel.getCreated().atOffset(ZoneOffset.UTC),
                                logModel.getMessage()
                        ),
                        () -> null,
                        () -> null
                )
                .invoke(logWasInserted -> {
                    if (logWasInserted) {
                        changeContext.add(logModel);
                    }
                });
    }
}
