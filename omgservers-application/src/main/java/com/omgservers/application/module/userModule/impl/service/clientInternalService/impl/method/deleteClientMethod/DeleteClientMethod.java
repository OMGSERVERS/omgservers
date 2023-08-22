package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.deleteClientMethod;

import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.DeleteClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.DeleteClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMethod {
    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientInternalRequest request);
}
