package com.omgservers.service.service.registry;

import com.omgservers.schema.service.registry.IssueTokenRequest;
import com.omgservers.schema.service.registry.IssueTokenResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RegistryService {

    Uni<IssueTokenResponse> issueToken(@Valid IssueTokenRequest request);
}
