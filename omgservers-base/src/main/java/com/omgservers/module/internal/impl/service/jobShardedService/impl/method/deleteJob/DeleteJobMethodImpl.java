package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.deleteJob;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.internal.DeleteJobShardedRequest;
import com.omgservers.dto.internal.DeleteJobShardedResponse;
import com.omgservers.module.internal.impl.operation.deleteJob.DeleteJobOperation;
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
    public Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardedRequest request) {
        DeleteJobShardedRequest.validate(request);

        final var shardKey = request.getShardKey();
        final var entity = request.getEntity();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteJobOperation.deleteJob(changeContext, sqlConnection, shardKey, entity))
                .map(ChangeContext::getResult)
                .map(DeleteJobShardedResponse::new);
    }
}
