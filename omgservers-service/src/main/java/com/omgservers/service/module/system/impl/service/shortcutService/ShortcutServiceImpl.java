package com.omgservers.service.module.system.impl.service.shortcutService;

import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.container.ContainerQualifierEnum;
import com.omgservers.model.dto.system.DeleteContainerRequest;
import com.omgservers.model.dto.system.DeleteContainerResponse;
import com.omgservers.model.dto.system.DeleteEntityRequest;
import com.omgservers.model.dto.system.DeleteEntityResponse;
import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.FindContainerRequest;
import com.omgservers.model.dto.system.FindContainerResponse;
import com.omgservers.model.dto.system.FindEntityRequest;
import com.omgservers.model.dto.system.FindEntityResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.GetContainerRequest;
import com.omgservers.model.dto.system.GetContainerResponse;
import com.omgservers.model.dto.system.GetEventRequest;
import com.omgservers.model.dto.system.GetEventResponse;
import com.omgservers.model.dto.system.GetJobRequest;
import com.omgservers.model.dto.system.GetJobResponse;
import com.omgservers.model.dto.system.HandleEventRequest;
import com.omgservers.model.dto.system.HandleEventResponse;
import com.omgservers.model.dto.system.RunContainerRequest;
import com.omgservers.model.dto.system.RunContainerResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.dto.system.StopContainerRequest;
import com.omgservers.model.dto.system.StopContainerResponse;
import com.omgservers.model.dto.system.SyncContainerRequest;
import com.omgservers.model.dto.system.SyncContainerResponse;
import com.omgservers.model.dto.system.SyncEntityRequest;
import com.omgservers.model.dto.system.SyncEntityResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.system.SyncJobResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.model.dto.system.UpdateEventsStatusRequest;
import com.omgservers.model.dto.system.UpdateEventsStatusResponse;
import com.omgservers.model.dto.system.ValidateCredentialsRequest;
import com.omgservers.model.dto.system.ValidateCredentialsResponse;
import com.omgservers.model.entitiy.EntityModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.model.index.IndexModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ShortcutServiceImpl implements ShortcutService {

    final SystemModule systemModule;

    @Override
    public Uni<IndexModel> findIndex(final String indexName) {
        final var request = new FindIndexRequest(indexName);
        return systemModule.getIndexService().findIndex(request)
                .map(FindIndexResponse::getIndex);
    }

    @Override
    public Uni<Boolean> syncIndex(final IndexModel index) {
        final var request = new SyncIndexRequest(index);
        return systemModule.getIndexService().syncIndex(request)
                .map(SyncIndexResponse::getCreated);
    }

    @Override
    public Uni<ServiceAccountModel> findServiceAccount(final String username) {
        final var request = new FindServiceAccountRequest(username);
        return systemModule.getServiceAccountService().findServiceAccount(request)
                .map(FindServiceAccountResponse::getServiceAccount);
    }

    @Override
    public Uni<Boolean> syncServiceAccount(final ServiceAccountModel serviceAccount) {
        final var request = new SyncServiceAccountRequest(serviceAccount);
        return systemModule.getServiceAccountService().syncServiceAccount(request)
                .map(SyncServiceAccountResponse::getCreated);
    }

    @Override
    public Uni<Boolean> validateCredentials(final String username, final String password) {
        final var request = new ValidateCredentialsRequest(username, password);
        return systemModule.getServiceAccountService().validateCredentials(request)
                .map(ValidateCredentialsResponse::getValid);
    }

    @Override
    public Uni<ContainerModel> getContainer(final Long id) {
        final var request = new GetContainerRequest(id);
        return systemModule.getContainerService().getContainer(request)
                .map(GetContainerResponse::getContainer);
    }

    @Override
    public Uni<ContainerModel> findRuntimeContainer(final Long runtimeId) {
        final var request = new FindContainerRequest(runtimeId, ContainerQualifierEnum.RUNTIME);
        return systemModule.getContainerService().findContainer(request)
                .map(FindContainerResponse::getContainer);
    }

    @Override
    public Uni<Boolean> syncContainer(final ContainerModel container) {
        final var request = new SyncContainerRequest(container);
        return systemModule.getContainerService().syncContainer(request)
                .map(SyncContainerResponse::getCreated);
    }

    @Override
    public Uni<Boolean> deleteContainer(final Long id) {
        final var request = new DeleteContainerRequest(id);
        return systemModule.getContainerService().deleteContainer(request)
                .map(DeleteContainerResponse::getDeleted);
    }

    @Override
    public Uni<Boolean> runContainer(final ContainerModel container) {
        final var request = new RunContainerRequest(container);
        return systemModule.getContainerService().runContainer(request)
                .map(RunContainerResponse::getWasRun);
    }

    @Override
    public Uni<Boolean> stopContainer(final Long id) {
        final var request = new StopContainerRequest(id, true);
        return systemModule.getContainerService().stopContainer(request)
                .map(StopContainerResponse::getStopped);
    }

    @Override
    public Uni<JobModel> getJob(final Long id) {
        final var request = new GetJobRequest(id);
        return systemModule.getJobService().getJob(request)
                .map(GetJobResponse::getJob);
    }

    @Override
    public Uni<JobModel> findMatchmakerJob(final Long matchmakerId) {
        final var request = new FindJobRequest(matchmakerId, matchmakerId, JobQualifierEnum.MATCHMAKER);
        return systemModule.getJobService().findJob(request)
                .map(FindJobResponse::getJob);
    }

    @Override
    public Uni<JobModel> findMatchJob(final Long matchmakerId, final Long matchId) {
        final var request = new FindJobRequest(matchmakerId, matchmakerId, JobQualifierEnum.MATCH);
        return systemModule.getJobService().findJob(request)
                .map(FindJobResponse::getJob);
    }

    @Override
    public Uni<Boolean> syncJob(final JobModel job) {
        final var request = new SyncJobRequest(job);
        return systemModule.getJobService().syncJob(request)
                .map(SyncJobResponse::getCreated);
    }

    @Override
    public Uni<Boolean> deleteJob(final Long id) {
        final var request = new DeleteJobRequest(id);
        return systemModule.getJobService().deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }

    @Override
    public Uni<Void> scheduleJob(final Long shardKey,
                                 final Long entityId,
                                 final JobQualifierEnum qualifier) {
        final var request = new ScheduleJobRequest(shardKey, entityId, qualifier);
        return systemModule.getJobService().scheduleJob(request);
    }

    @Override
    public Uni<Void> unscheduleJob(final Long shardKey,
                                   final Long entityId,
                                   final JobQualifierEnum qualifier) {
        final var request = new UnscheduleJobRequest(shardKey, entityId, qualifier);
        return systemModule.getJobService().unscheduleJob(request);
    }

    @Override
    public Uni<EventModel> getEvent(final Long id) {
        final var request = new GetEventRequest(id);
        return systemModule.getEventService().getEvent(request)
                .map(GetEventResponse::getEvent);
    }

    @Override
    public Uni<Boolean> handleEvent(final EventModel event) {
        final var request = new HandleEventRequest(event);
        return systemModule.getHandlerService().handleEvent(request)
                .map(HandleEventResponse::getResult);
    }

    @Override
    public Uni<Boolean> updateEventStatus(final Long id, final EventStatusEnum status) {
        final var request = new UpdateEventsStatusRequest(Collections.singletonList(id), status);
        return systemModule.getEventService().updateEventsStatus(request)
                .map(UpdateEventsStatusResponse::getUpdated);
    }

    @Override
    public Uni<EntityModel> findEntity(final Long entityId) {
        final var request = new FindEntityRequest(entityId);
        return systemModule.getEntityService().findEntity(request)
                .map(FindEntityResponse::getEntity);
    }

    @Override
    public Uni<Boolean> syncEntity(final EntityModel entity) {
        final var request = new SyncEntityRequest(entity);
        return systemModule.getEntityService().syncEntity(request)
                .map(SyncEntityResponse::getCreated);
    }

    @Override
    public Uni<Boolean> deleteEntity(final Long id) {
        final var request = new DeleteEntityRequest(id);
        return systemModule.getEntityService().deleteEntity(request)
                .map(DeleteEntityResponse::getDeleted);
    }

    @Override
    public Uni<Boolean> findAndDeleteEntity(final Long entityId) {
        return findEntity(entityId)
                .flatMap(entity -> deleteEntity(entity.getId()));
    }
}
