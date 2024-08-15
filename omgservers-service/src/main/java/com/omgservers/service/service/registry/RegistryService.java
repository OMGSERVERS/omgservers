package com.omgservers.service.service.registry;

import com.omgservers.service.service.registry.dto.IssueTokenRequest;
import com.omgservers.service.service.registry.dto.IssueTokenResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RegistryService {

    Uni<IssueTokenResponse> issueToken(@Valid IssueTokenRequest request);
}
