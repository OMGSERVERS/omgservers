package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteProjectPermissions;

import com.omgservers.model.dto.support.DeleteProjectPermissionsSupportRequest;
import com.omgservers.model.dto.support.DeleteProjectPermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectPermissionsMethod {
    Uni<DeleteProjectPermissionsSupportResponse> deleteProjectPermissions(
            DeleteProjectPermissionsSupportRequest request);
}
