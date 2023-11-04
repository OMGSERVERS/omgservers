package com.omgservers.module.system.impl.service.jobService.impl.method.deleteJob;

import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.module.system.impl.operation.deleteJob.DeleteJobOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteJobMethodImpl implements DeleteJobMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteJobOperation deleteJobOperation;

    @Override
    public Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request) {
        final var shardKey = request.getShardKey();
        final var entityId = request.getEntityId();
        final var qualifier = request.getQualifier();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteJobOperation.deleteJob(changeContext, sqlConnection, shardKey, entityId, qualifier)
                )
                .map(ChangeContext::getResult)
                .map(DeleteJobResponse::new);
    }
}
