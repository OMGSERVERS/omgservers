package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.getVersionStatusDeveloperOperation;

import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.GetVersionStatusHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.GetVersionStatusHelpResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetVersionInternalRequest;
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
    public Uni<GetVersionStatusHelpResponse> getVersionStatus(final GetVersionStatusHelpRequest request) {
        GetVersionStatusHelpRequest.validate(request);

        final var uuid = request.getUuid();
        final var getVersionServiceRequest = new GetVersionInternalRequest(uuid);
        return versionModule.getVersionInternalService().getVersion(getVersionServiceRequest)
                .map(getVersionServiceResponse -> getVersionServiceResponse.getVersion().getStatus())
                .map(GetVersionStatusHelpResponse::new);
    }
}
