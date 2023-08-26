package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.deleteJob;

import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.operation.deleteJob.DeleteJobOperation;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.internalModule.DeleteJobShardRequest;
import com.omgservers.dto.internalModule.DeleteJobShardedResponse;
import com.omgservers.model.event.body.JobDeletedEventBodyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteJobMethodImpl implements DeleteJobMethod {

    final InternalModule internalModule;

    final DeleteJobOperation deleteJobOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardRequest request) {
        DeleteJobShardRequest.validate(request);

        final var shardKey = request.getShardKey();
        final var entity = request.getEntity();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> deleteJobOperation
                                .deleteJob(sqlConnection, shardKey, entity),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create(String.format("Job was deleted, " +
                                        "shardKey=%d, entity=%d", shardKey, entity));
                            } else {
                                return null;
                            }
                        },
                        deleted -> {
                            if (deleted) {
                                return new JobDeletedEventBodyModel(shardKey, entity);
                            } else {
                                return null;
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(DeleteJobShardedResponse::new);
    }
}
