package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteDeveloper;

import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDeveloperMethod {
    Uni<DeleteDeveloperSupportResponse> deleteDeveloper(DeleteDeveloperSupportRequest request);
}
