package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchmakerMatchClients;

import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerMatchClientsMethod {
    Uni<ViewMatchmakerMatchClientsResponse> viewMatchmakerMatchClients(ViewMatchmakerMatchClientsRequest request);
}
