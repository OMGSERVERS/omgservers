package com.omgservers.service.module.runtime.impl.service.webService;

import com.omgservers.model.dto.runtime.CountRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.CountRuntimeClientsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeClientRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeClientResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.FindRuntimeClientRequest;
import com.omgservers.model.dto.runtime.FindRuntimeClientResponse;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeClientRequest;
import com.omgservers.model.dto.runtime.GetRuntimeClientResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.InterchangeRequest;
import com.omgservers.model.dto.runtime.InterchangeResponse;
import com.omgservers.model.dto.runtime.SyncClientCommandRequest;
import com.omgservers.model.dto.runtime.SyncClientCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeClientRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeClientResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {
    Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request);

    Uni<SyncRuntimeResponse> syncLobbyRuntime(SyncRuntimeRequest request);

    Uni<DeleteRuntimeResponse> deleteRuntime(DeleteRuntimeRequest request);

    Uni<SyncRuntimePermissionResponse> syncRuntimePermission(SyncRuntimePermissionRequest request);

    Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(ViewRuntimePermissionsRequest request);

    Uni<FindRuntimePermissionResponse> findRuntimePermission(FindRuntimePermissionRequest request);

    Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(DeleteRuntimePermissionRequest request);

    Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(ViewRuntimeCommandsRequest request);

    Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(SyncRuntimeCommandRequest request);

    Uni<SyncClientCommandResponse> syncClientCommand(SyncClientCommandRequest request);

    Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(DeleteRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(DeleteRuntimeCommandsRequest request);

    Uni<GetRuntimeClientResponse> getRuntimeClient(GetRuntimeClientRequest request);

    Uni<FindRuntimeClientResponse> findRuntimeClient(FindRuntimeClientRequest request);

    Uni<ViewRuntimeClientsResponse> viewRuntimeClients(ViewRuntimeClientsRequest request);

    Uni<CountRuntimeClientsResponse> countRuntimeClients(CountRuntimeClientsRequest request);

    Uni<SyncRuntimeClientResponse> syncRuntimeClient(SyncRuntimeClientRequest request);

    Uni<DeleteRuntimeClientResponse> deleteRuntimeClient(DeleteRuntimeClientRequest request);

    Uni<InterchangeResponse> interchange(InterchangeRequest request);
}
