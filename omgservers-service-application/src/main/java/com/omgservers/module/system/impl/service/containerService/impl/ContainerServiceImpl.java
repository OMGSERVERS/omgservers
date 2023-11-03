package com.omgservers.module.system.impl.service.containerService.impl;

import com.omgservers.model.dto.system.DeleteContainerRequest;
import com.omgservers.model.dto.system.DeleteContainerResponse;
import com.omgservers.model.dto.system.FindContainerRequest;
import com.omgservers.model.dto.system.FindContainerResponse;
import com.omgservers.model.dto.system.GetContainerRequest;
import com.omgservers.model.dto.system.GetContainerResponse;
import com.omgservers.model.dto.system.RunContainerRequest;
import com.omgservers.model.dto.system.RunContainerResponse;
import com.omgservers.model.dto.system.StopContainerRequest;
import com.omgservers.model.dto.system.StopContainerResponse;
import com.omgservers.model.dto.system.SyncContainerRequest;
import com.omgservers.model.dto.system.SyncContainerResponse;
import com.omgservers.module.system.impl.service.containerService.ContainerService;
import com.omgservers.module.system.impl.service.containerService.impl.method.deleteContainer.DeleteContainerMethod;
import com.omgservers.module.system.impl.service.containerService.impl.method.findContainer.FindContainerMethod;
import com.omgservers.module.system.impl.service.containerService.impl.method.getContainer.GetContainerMethod;
import com.omgservers.module.system.impl.service.containerService.impl.method.runContainer.RunContainerMethod;
import com.omgservers.module.system.impl.service.containerService.impl.method.stopContainer.StopContainerMethod;
import com.omgservers.module.system.impl.service.containerService.impl.method.syncContainer.SyncContainerMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ContainerServiceImpl implements ContainerService {

    final DeleteContainerMethod deleteContainerMethod;
    final FindContainerMethod findContainerMethod;
    final StopContainerMethod stopContainerMethod;
    final SyncContainerMethod syncContainerMethod;
    final GetContainerMethod getContainerMethod;
    final RunContainerMethod runContainerMethod;

    @Override
    public Uni<GetContainerResponse> getContainer(@Valid final GetContainerRequest request) {
        return getContainerMethod.getContainer(request);
    }

    @Override
    public Uni<FindContainerResponse> findContainer(@Valid final FindContainerRequest request) {
        return findContainerMethod.findContainer(request);
    }

    @Override
    public Uni<SyncContainerResponse> syncContainer(@Valid final SyncContainerRequest request) {
        return syncContainerMethod.syncContainer(request);
    }

    @Override
    public Uni<DeleteContainerResponse> deleteContainer(@Valid final DeleteContainerRequest request) {
        return deleteContainerMethod.deleteContainer(request);
    }

    @Override
    public Uni<RunContainerResponse> runContainer(@Valid final RunContainerRequest request) {
        return runContainerMethod.runContainer(request);
    }

    @Override
    public Uni<StopContainerResponse> stopContainer(final StopContainerRequest request) {
        return stopContainerMethod.stopContainer(request);
    }
}
