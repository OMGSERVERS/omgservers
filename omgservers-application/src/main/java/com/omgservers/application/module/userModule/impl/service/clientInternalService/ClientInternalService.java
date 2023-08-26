package com.omgservers.application.module.userModule.impl.service.clientInternalService;

import com.omgservers.dto.userModule.DeleteClientShardRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import com.omgservers.dto.userModule.GetClientShardRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.dto.userModule.SyncClientShardRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ClientInternalService {

    Uni<SyncClientInternalResponse> syncClient(SyncClientShardRequest request);

    Uni<GetClientInternalResponse> getClient(GetClientShardRequest request);

    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientShardRequest request);
}
