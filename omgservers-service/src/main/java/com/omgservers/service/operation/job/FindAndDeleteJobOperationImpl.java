package com.omgservers.service.operation.job;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.server.job.JobService;
import com.omgservers.service.server.job.dto.DeleteJobRequest;
import com.omgservers.service.server.job.dto.DeleteJobResponse;
import com.omgservers.service.server.job.dto.FindJobRequest;
import com.omgservers.service.server.job.dto.FindJobResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class FindAndDeleteJobOperationImpl implements FindAndDeleteJobOperation {

    final JobService jobService;

    @Override
    public Uni<Void> execute(final Long shardKey, final Long entityId) {
        return findJob(shardKey, entityId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(job -> deleteJob(job.getId()))
                .replaceWithVoid();
    }

    @Override
    public Uni<Void> execute(Long entityId) {
        return execute(entityId, entityId);
    }

    Uni<JobModel> findJob(final Long shardKey, final Long entityId) {
        final var request = new FindJobRequest(shardKey, entityId);
        return jobService.findJob(request)
                .map(FindJobResponse::getJob);
    }

    Uni<Boolean> deleteJob(final Long id) {
        final var request = new DeleteJobRequest(id);
        return jobService.deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }
}
