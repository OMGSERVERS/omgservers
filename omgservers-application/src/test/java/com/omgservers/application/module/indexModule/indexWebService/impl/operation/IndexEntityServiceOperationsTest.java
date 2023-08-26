package com.omgservers.application.module.indexModule.indexWebService.impl.operation;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.base.module.internal.impl.operation.deleteIndex.DeleteIndexOperation;
import com.omgservers.base.module.internal.impl.operation.getIndex.GetIndexOperation;
import com.omgservers.base.module.internal.impl.operation.upsertIndex.UpsertIndexOperation;
import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.model.index.IndexModel;
import com.omgservers.base.factory.IndexModelFactory;
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
    IndexModelFactory indexModelFactory;

    @Inject
    PgPool pgPool;

    @Test
    void whenRegularUsage_thenOk() {
        final var name = UUID.randomUUID().toString();
        final var index = indexModelFactory.create(name, IndexConfigModel.create(32));

        syncIndex(index);
        final var index1 = getIndex(name);
        assertEquals(index, index1);

        final var newVersion = index.getVersion() + 1;
        index.setVersion(newVersion);
        syncIndex(index);

        final var index2 = getIndex(name);
        assertEquals(newVersion, index2.getVersion());

        deleteIndex(index.getId());

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

    void deleteIndex(Long id) {
        pgPool.withTransaction(sqlConnection -> deleteIndexOperation
                        .deleteIndex(sqlConnection, id))
                .await().atMost(Duration.ofSeconds(1));
    }
}
