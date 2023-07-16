package com.omgservers.application.module.indexModule.indexWebService.impl.operation;

import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.internalModule.impl.operation.deleteIndexOperation.DeleteIndexOperation;
import com.omgservers.application.module.internalModule.impl.operation.getIndexOperation.GetIndexOperation;
import com.omgservers.application.module.internalModule.impl.operation.upsertIndexOperation.UpsertIndexOperation;
import com.omgservers.application.module.internalModule.model.index.IndexConfigModel;
import com.omgservers.application.module.internalModule.model.index.IndexModel;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@QuarkusTest
public class IndexEntityServiceOperationsTest extends Assertions {

    @Inject
    GetIndexOperation getIndexOperation;

    @Inject
    UpsertIndexOperation syncIndexOperation;

    @Inject
    DeleteIndexOperation deleteIndexOperation;

    @Inject
    PgPool pgPool;

    @Test
    void whenRegularUsage_thenOk() {
        final var name = UUID.randomUUID().toString();
        final var index = IndexModel.create(name, IndexConfigModel.create(32));

        syncIndex(index);
        final var index1 = getIndex(name);
        assertEquals(index, index1);

        final var newVersion = index.getVersion() + 1;
        index.setVersion(newVersion);
        syncIndex(index);

        final var index2 = getIndex(name);
        assertEquals(newVersion, index2.getVersion());

        deleteIndex(index.getUuid());

        assertThrows(ServerSideNotFoundException.class, () -> getIndex(name));
    }

    IndexModel getIndex(String name) {
        return pgPool.withTransaction(sqlConnection -> getIndexOperation
                        .getIndex(sqlConnection, name))
                .await().atMost(Duration.ofSeconds(1));
    }

    void syncIndex(IndexModel indexModel) {
        pgPool.withTransaction(sqlConnection -> syncIndexOperation
                        .upsertIndex(sqlConnection, indexModel))
                .await().atMost(Duration.ofSeconds(1));
    }

    void deleteIndex(UUID uuid) {
        pgPool.withTransaction(sqlConnection -> deleteIndexOperation
                        .deleteIndex(sqlConnection, uuid))
                .await().atMost(Duration.ofSeconds(1));
    }
}
