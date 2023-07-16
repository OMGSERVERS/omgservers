package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.deleteClientMethod;

import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.DeleteClientInternalRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMethod {
    Uni<Void> deleteClient(DeleteClientInternalRequest request);
}
