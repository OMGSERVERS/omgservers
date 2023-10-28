package com.omgservers.module.system.impl.service.containerService.impl.method.stopContainer;

import com.omgservers.model.dto.internal.StopContainerRequest;
import com.omgservers.model.dto.internal.StopContainerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class StopContainerMethodImpl implements StopContainerMethod {

    @Override
    public Uni<StopContainerResponse> stopContainer(final StopContainerRequest request) {
        return Uni.createFrom().item(new StopContainerResponse(false));
    }
}
