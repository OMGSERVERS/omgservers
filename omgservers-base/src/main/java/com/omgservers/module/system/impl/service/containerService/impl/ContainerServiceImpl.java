package com.omgservers.module.system.impl.service.containerService.impl;

import com.omgservers.dto.internal.DeleteContainerRequest;
import com.omgservers.dto.internal.DeleteContainerResponse;
import com.omgservers.dto.internal.GetContainerRequest;
import com.omgservers.dto.internal.GetContainerResponse;
import com.omgservers.dto.internal.RunContainerRequest;
import com.omgservers.dto.internal.RunContainerResponse;
import com.omgservers.dto.internal.StopContainerRequest;
import com.omgservers.dto.internal.StopContainerResponse;
import com.omgservers.dto.internal.SyncContainerRequest;
import com.omgservers.dto.internal.SyncContainerResponse;
import com.omgservers.module.system.impl.service.containerService.ContainerService;
import com.omgservers.module.system.impl.service.containerService.impl.method.deleteContainer.DeleteContainerMethod;
import com.omgservers.module.system.impl.service.containerService.impl.method.getContainer.GetContainerMethod;
import com.omgservers.module.system.impl.service.containerService.impl.method.runContainer.RunContainerMethod;
import com.omgservers.module.system.impl.service.containerService.impl.method.stopContainer.StopContainerMethod;
import com.omgservers.module.system.impl.service.containerService.impl.method.syncContainer.SyncContainerMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ContainerServiceImpl implements ContainerService {

    final DeleteContainerMethod deleteContainerMethod;
    final StopContainerMethod stopContainerMethod;
    final SyncContainerMethod syncContainerMethod;
    final GetContainerMethod getContainerMethod;
    final RunContainerMethod runContainerMethod;

    @Override
    public Uni<GetContainerResponse> getContainer(final GetContainerRequest request) {
        return getContainerMethod.getContainer(request);
    }

    @Override
    public Uni<SyncContainerResponse> syncContainer(final SyncContainerRequest request) {
        return syncContainerMethod.syncContainer(request);
    }

    @Override
    public Uni<DeleteContainerResponse> deleteContainer(final DeleteContainerRequest request) {
        return deleteContainerMethod.deleteContainer(request);
    }

    @Override
    public Uni<RunContainerResponse> runContainer(final RunContainerRequest request) {
        return runContainerMethod.runContainer(request);
    }

    @Override
    public Uni<StopContainerResponse> stopContainer(final StopContainerRequest request) {
        return stopContainerMethod.stopContainer(request);
    }
}
