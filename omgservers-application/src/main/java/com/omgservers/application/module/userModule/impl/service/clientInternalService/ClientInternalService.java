package com.omgservers.application.module.userModule.impl.service.clientInternalService;

import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.CreateClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.DeleteClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.GetClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.CreateClientInternalResponse;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.GetClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ClientInternalService {

    Uni<CreateClientInternalResponse> createClient(CreateClientInternalRequest request);

    Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request);

    Uni<Void> deleteClient(DeleteClientInternalRequest request);
}
