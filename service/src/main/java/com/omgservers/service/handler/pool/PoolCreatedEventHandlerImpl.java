package com.omgservers.service.handler.pool;

import com.omgservers.model.dto.pool.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.pool.GetPoolResponse;
import com.omgservers.model.dto.system.job.SyncJobRequest;
import com.omgservers.model.dto.system.job.SyncJobResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.pool.PoolCreatedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.pool.PoolModel;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.system.JobModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolCreatedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;
    final PoolModule poolModule;

    final GetConfigOperation getConfigOperation;

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
        log.debug("Handle event, {}", event);

        final var body = (PoolCreatedEventBodyModel) event.getBody();
        final var poolId = body.getId();

        return getPool(poolId)
                .flatMap(pool -> {
                    log.info("Pool was created, pool={}", poolId);

                    final var idempotencyKey = event.getId().toString();

                    return syncPoolJob(poolId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<PoolModel> getPool(final Long id) {
        final var request = new GetPoolRequest(id);
        return poolModule.getPoolService().getPool(request)
                .map(GetPoolResponse::getPool);
    }

    Uni<Boolean> syncPoolJob(final Long runtimeId,
                             final String idempotencyKey) {
        final var job = jobModelFactory.create(JobQualifierEnum.POOL, runtimeId, idempotencyKey);

        final var syncEventRequest = new SyncJobRequest(job);
        return systemModule.getJobService().syncJobWithIdempotency(syncEventRequest)
                .map(SyncJobResponse::getCreated);
    }
}
