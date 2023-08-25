package com.omgservers.application.module.userModule.impl.service.clientInternalService;

import com.omgservers.dto.userModule.DeleteClientInternalRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import com.omgservers.dto.userModule.GetClientInternalRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.dto.userModule.SyncClientInternalRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ClientInternalService {

    Uni<SyncClientInternalResponse> syncClient(SyncClientInternalRequest request);

    Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request);

    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientInternalRequest request);
}
