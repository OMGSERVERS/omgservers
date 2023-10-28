package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchClients;

import com.omgservers.model.dto.matchmaker.ViewMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchClientsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchClientsMethod {
    Uni<ViewMatchClientsResponse> viewMatchClients(ViewMatchClientsRequest request);
}
