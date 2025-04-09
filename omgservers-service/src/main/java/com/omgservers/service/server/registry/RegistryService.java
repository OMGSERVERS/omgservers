package com.omgservers.service.server.registry;

import com.omgservers.service.server.registry.dto.IssueRegistryTokensRequest;
import com.omgservers.service.server.registry.dto.IssueRegistryTokensResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RegistryService {

    Uni<IssueRegistryTokensResponse> execute(@Valid IssueRegistryTokensRequest request);
}
