package com.omgservers.application.module.userModule.impl.service.clientInternalService;

import com.omgservers.dto.userModule.DeleteClientRoutedRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import com.omgservers.dto.userModule.GetClientRoutedRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.dto.userModule.SyncClientRoutedRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ClientInternalService {

    Uni<SyncClientInternalResponse> syncClient(SyncClientRoutedRequest request);

    Uni<GetClientInternalResponse> getClient(GetClientRoutedRequest request);

    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientRoutedRequest request);
}
