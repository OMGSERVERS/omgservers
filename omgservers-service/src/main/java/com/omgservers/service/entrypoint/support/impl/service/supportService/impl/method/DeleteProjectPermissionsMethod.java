package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectPermissionsMethod {
    Uni<DeleteProjectPermissionsSupportResponse> execute(DeleteProjectPermissionsSupportRequest request);
}
