package com.omgservers.service.server.service.job.impl.method.deleteJob;

import com.omgservers.schema.service.system.job.DeleteJobRequest;
import com.omgservers.schema.service.system.job.DeleteJobResponse;
import com.omgservers.service.server.service.job.operation.deleteJob.DeleteJobOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteJobMethodImpl implements DeleteJobMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteJobOperation deleteJobOperation;

    @Override
    public Uni<DeleteJobResponse> deleteJob(final DeleteJobRequest request) {
        log.debug("Delete job, request={}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteJobOperation.deleteJob(changeContext, sqlConnection, id))
                .map(ChangeContext::getResult)
                .map(DeleteJobResponse::new);
    }
}
