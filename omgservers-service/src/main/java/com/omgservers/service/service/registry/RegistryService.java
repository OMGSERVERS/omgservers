package com.omgservers.service.service.registry;

import com.omgservers.service.service.registry.dto.IssueRegistryTokensRequest;
import com.omgservers.service.service.registry.dto.IssueRegistryTokensResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RegistryService {

    Uni<IssueRegistryTokensResponse> execute(@Valid IssueRegistryTokensRequest request);
}
