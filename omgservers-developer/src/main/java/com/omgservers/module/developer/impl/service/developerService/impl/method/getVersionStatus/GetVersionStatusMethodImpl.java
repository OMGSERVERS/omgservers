package com.omgservers.module.developer.impl.service.developerService.impl.method.getVersionStatus;

import com.omgservers.dto.developer.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developer.GetVersionStatusDeveloperResponse;
import com.omgservers.module.version.VersionModule;
import com.omgservers.dto.version.GetVersionShardedRequest;
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
        final var getVersionServiceRequest = new GetVersionShardedRequest(id);
        return versionModule.getVersionShardedService().getVersion(getVersionServiceRequest)
                .map(getVersionServiceResponse -> getVersionServiceResponse.getVersion().getStatus())
                .map(GetVersionStatusDeveloperResponse::new);
    }
}
