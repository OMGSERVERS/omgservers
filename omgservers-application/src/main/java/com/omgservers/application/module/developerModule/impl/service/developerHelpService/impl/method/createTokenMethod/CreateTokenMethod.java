package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createTokenMethod;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateTokenHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateTokenHelpResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenHelpResponse> createToken(CreateTokenHelpRequest request);
}
