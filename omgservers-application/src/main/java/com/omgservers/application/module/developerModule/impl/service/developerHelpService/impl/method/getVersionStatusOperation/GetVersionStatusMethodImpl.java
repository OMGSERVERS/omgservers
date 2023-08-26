package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.getVersionStatusOperation;

import com.omgservers.dto.developerModule.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperResponse;
import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.dto.versionModule.GetVersionShardRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetVersionStatusMethodImpl implements GetVersionStatusMethod {

    final VersionModule versionModule;

    @Override
    public Uni<GetVersionStatusDeveloperResponse> getVersionStatus(final GetVersionStatusDeveloperRequest request) {
        GetVersionStatusDeveloperRequest.validate(request);

        final var id = request.getId();
        final var getVersionServiceRequest = new GetVersionShardRequest(id);
        return versionModule.getVersionInternalService().getVersion(getVersionServiceRequest)
                .map(getVersionServiceResponse -> getVersionServiceResponse.getVersion().getStatus())
                .map(GetVersionStatusDeveloperResponse::new);
    }
}
