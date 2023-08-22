package com.omgservers.application.module.userModule.impl.service.clientInternalService;

import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.DeleteClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.GetClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.SyncClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.DeleteClientInternalResponse;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.GetClientInternalResponse;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.SyncClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ClientInternalService {

    Uni<SyncClientInternalResponse> syncClient(SyncClientInternalRequest request);

    Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request);

    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientInternalRequest request);
}
