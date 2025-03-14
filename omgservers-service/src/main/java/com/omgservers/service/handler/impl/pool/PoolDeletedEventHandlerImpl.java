package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.pool.PoolShard;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.DeleteJobRequest;
import com.omgservers.service.service.job.dto.DeleteJobResponse;
import com.omgservers.service.service.job.dto.FindJobRequest;
import com.omgservers.service.service.job.dto.FindJobResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolDeletedEventHandlerImpl implements EventHandler {

    final PoolShard poolShard;

    final JobService jobService;

    final PoolModelFactory poolModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (PoolDeletedEventBodyModel) event.getBody();
        final var poolId = body.getId();

        return getPool(poolId)
                .flatMap(pool -> {
                    log.debug("Deleted, {}", pool);

                    return findAndDeleteJob(poolId);
                })
                .replaceWithVoid();
    }

    Uni<PoolModel> getPool(final Long id) {
        final var request = new GetPoolRequest(id);
        return poolShard.getPoolService().execute(request)
                .map(GetPoolResponse::getPool);
    }

    Uni<Void> findAndDeleteJob(final Long poolId) {
        return findJob(poolId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(job -> deleteJob(job.getId()))
                .replaceWithVoid();
    }

    Uni<JobModel> findJob(final Long poolId) {
        final var request = new FindJobRequest(poolId, poolId);
        return jobService.findJob(request)
                .map(FindJobResponse::getJob);
    }

    Uni<Boolean> deleteJob(final Long id) {
        final var request = new DeleteJobRequest(id);
        return jobService.deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }
}
