package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDeveloperMethod {
    Uni<DeleteDeveloperSupportResponse> execute(DeleteDeveloperSupportRequest request);
}
