package com.omgservers.application.module.internalModule.impl.service.jobInternalService.impl.method.deleteJobMethod;

import com.omgservers.application.module.internalModule.impl.operation.deleteJobOperation.DeleteJobOperation;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.DeleteJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.DeleteJobInternalResponse;
import com.omgservers.application.module.internalModule.model.event.body.JobDeletedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteJobMethodImpl implements DeleteJobMethod {

    final EventHelpService eventInternalService;

    final DeleteJobOperation deleteJobOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request) {
        DeleteJobInternalRequest.validate(request);

        final var shardKey = request.getShardKey();
        final var entity = request.getEntity();
        return changeOperation.changeWithEvent(request,
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
                )
                .map(DeleteJobInternalResponse::new);
    }
}
