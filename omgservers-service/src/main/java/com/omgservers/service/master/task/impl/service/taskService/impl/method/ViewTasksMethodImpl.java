package com.omgservers.service.master.task.impl.service.taskService.impl.method;

import com.omgservers.schema.master.task.ViewTasksRequest;
import com.omgservers.schema.master.task.ViewTasksResponse;
import com.omgservers.service.master.task.impl.operation.SelectTasksOperation;
import com.omgservers.service.operation.server.CalculateShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ViewTasksMethodImpl implements ViewTasksMethod {

    final SelectTasksOperation selectTasksOperation;

    final CalculateShardOperation calculateShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTasksResponse> execute(final ViewTasksRequest request) {
        log.debug("{}", request);

        final var uri = request.getUri();

        return pgPool.withTransaction(selectTasksOperation::execute)
                .flatMap(allTasks -> Multi.createFrom().iterable(allTasks)
                        .onItem().transformToUniAndConcatenate(task -> {
                            final var shardKey = task.getShardKey().toString();
                            return calculateShardOperation.execute(shardKey)
                                    .flatMap(shardModel -> {
                                        if (shardModel.uri().equals(uri)) {
                                            return Uni.createFrom().item(task);
                                        } else {
                                            return Uni.createFrom().nullItem();
                                        }
                                    });
                        })
                        .collect().asList()
                        .map(tasks -> tasks.stream().filter(Objects::nonNull).toList()))
                .map(ViewTasksResponse::new);
    }
}
