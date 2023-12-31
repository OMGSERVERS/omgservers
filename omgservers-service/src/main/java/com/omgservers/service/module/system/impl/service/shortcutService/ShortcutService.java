package com.omgservers.service.module.system.impl.service.shortcutService;

import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.entitiy.EntityModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.model.index.IndexModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import io.smallrye.mutiny.Uni;

public interface ShortcutService {

    Uni<IndexModel> findIndex(String indexName);

    Uni<Boolean> syncIndex(IndexModel index);

    Uni<ServiceAccountModel> findServiceAccount(String username);

    Uni<Boolean> syncServiceAccount(ServiceAccountModel serviceAccount);

    Uni<Boolean> validateCredentials(String username, String password);

    Uni<ContainerModel> getContainer(Long id);

    Uni<ContainerModel> findRuntimeContainer(Long runtimeId);

    Uni<Boolean> syncContainer(ContainerModel container);

    Uni<Boolean> deleteContainer(Long id);

    Uni<Boolean> runContainer(ContainerModel container);

    Uni<Boolean> stopContainer(Long id);

    Uni<JobModel> getJob(Long id);

    Uni<JobModel> findStageJob(Long tenantId, Long stageId);

    Uni<JobModel> findMatchmakerJob(Long matchmakerId);

    Uni<JobModel> findMatchJob(Long matchmakerId, Long matchId);

    Uni<Boolean> syncJob(JobModel job);

    Uni<Boolean> deleteJob(Long id);

    Uni<Void> scheduleJob(Long shardKey, Long entityId, JobQualifierEnum qualifier);

    Uni<Void> unscheduleJob(Long shardKey, Long entityId, JobQualifierEnum qualifier);

    Uni<EventModel> getEvent(Long id);

    Uni<Boolean> handleEvent(EventModel event);

    Uni<Boolean> updateEventStatus(Long id, EventStatusEnum status);

    Uni<EntityModel> findEntity(Long entityId);

    Uni<Boolean> syncEntity(EntityModel entity);

    Uni<Boolean> deleteEntity(Long id);

    Uni<Boolean> findAndDeleteEntity(final Long entityId);
}
