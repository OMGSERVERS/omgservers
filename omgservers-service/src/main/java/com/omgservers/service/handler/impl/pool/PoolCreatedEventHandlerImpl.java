package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolCreatedEventBodyModel;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.system.JobModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.pool.PoolShard;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.SyncJobRequest;
import com.omgservers.service.service.job.dto.SyncJobResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolCreatedEventHandlerImpl implements EventHandler {

    final PoolShard poolShard;

    final JobService jobService;

    final GetServiceConfigOperation getServiceConfigOperation;

    final PoolServerModelFactory poolServerModelFactory;
    final EventModelFactory eventModelFactory;
    final PoolModelFactory poolModelFactory;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (PoolCreatedEventBodyModel) event.getBody();
        final var poolId = body.getId();

        return getPool(poolId)
                .flatMap(pool -> {
                    log.debug("Created, {}", pool);

                    final var idempotencyKey = event.getId().toString();

                    return syncPoolJob(poolId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<PoolModel> getPool(final Long id) {
        final var request = new GetPoolRequest(id);
        return poolShard.getPoolService().execute(request)
                .map(GetPoolResponse::getPool);
    }

    Uni<Boolean> syncPoolJob(final Long runtimeId,
                             final String idempotencyKey) {
        final var job = jobModelFactory.create(JobQualifierEnum.POOL,
                runtimeId,
                runtimeId,
                idempotencyKey);

        final var syncEventRequest = new SyncJobRequest(job);
        return jobService.syncJobWithIdempotency(syncEventRequest)
                .map(SyncJobResponse::getCreated);
    }
}
