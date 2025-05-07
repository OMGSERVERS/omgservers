package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateDeveloperAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperAliasSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateDeveloperAliasMethod {
    Uni<CreateDeveloperAliasSupportResponse> execute(CreateDeveloperAliasSupportRequest request);
}
