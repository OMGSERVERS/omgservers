package com.omgservers.service.module.runtime.impl.service.runtimeService;

import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.FindRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.FindRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RuntimeService {

    Uni<GetRuntimeResponse> getRuntime(@Valid GetRuntimeRequest request);

    Uni<SyncRuntimeResponse> syncRuntime(@Valid SyncRuntimeRequest request);

    Uni<DeleteRuntimeResponse> deleteRuntime(@Valid DeleteRuntimeRequest request);

    Uni<SyncRuntimePermissionResponse> syncRuntimePermission(@Valid SyncRuntimePermissionRequest request);

    Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(@Valid ViewRuntimePermissionsRequest request);

    Uni<FindRuntimePermissionResponse> findRuntimePermission(@Valid FindRuntimePermissionRequest request);

    Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(@Valid DeleteRuntimePermissionRequest request);

    Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(@Valid ViewRuntimeCommandsRequest request);

    Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(@Valid SyncRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(@Valid DeleteRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(@Valid DeleteRuntimeCommandsRequest request);

    Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(@Valid SyncRuntimeGrantRequest request);

    Uni<ViewRuntimeGrantsResponse> viewRuntimeGrants(@Valid ViewRuntimeGrantsRequest request);

    Uni<FindRuntimeGrantResponse> findRuntimeGrant(@Valid FindRuntimeGrantRequest request);

    Uni<DeleteRuntimeGrantResponse> deleteRuntimeGrant(@Valid DeleteRuntimeGrantRequest request);
}
