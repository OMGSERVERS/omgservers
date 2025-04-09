package com.omgservers.service.server.job.impl.method.deleteJob;

import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.server.job.dto.DeleteJobRequest;
import com.omgservers.service.server.job.dto.DeleteJobResponse;
import com.omgservers.service.server.job.operation.DeleteJobOperation;
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
        log.trace("{}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteJobOperation.deleteJob(changeContext, sqlConnection, id))
                .map(ChangeContext::getResult)
                .map(DeleteJobResponse::new);
    }
}
