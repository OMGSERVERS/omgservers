package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.getVersionStatusOperation;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.GetVersionStatusHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.GetVersionStatusHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionStatusMethod {
    Uni<GetVersionStatusHelpResponse> getVersionStatus(GetVersionStatusHelpRequest request);
}
