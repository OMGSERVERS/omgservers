package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createDefaultPoolServer;

import com.omgservers.model.dto.support.CreateDefaultPoolServerSupportRequest;
import com.omgservers.model.dto.support.CreateDefaultPoolServerSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateDefaultPoolServerMethod {
    Uni<CreateDefaultPoolServerSupportResponse> createDefaultPoolServer(CreateDefaultPoolServerSupportRequest request);
}
