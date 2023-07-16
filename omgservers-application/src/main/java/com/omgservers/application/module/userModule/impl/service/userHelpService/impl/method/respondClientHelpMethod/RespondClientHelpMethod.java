package com.omgservers.application.module.userModule.impl.service.userHelpService.impl.method.respondClientHelpMethod;

import com.omgservers.application.module.userModule.impl.service.userHelpService.request.RespondClientHelpRequest;
import io.smallrye.mutiny.Uni;

public interface RespondClientHelpMethod {
    Uni<Void> respondClient(RespondClientHelpRequest request);
}
