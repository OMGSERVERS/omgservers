package com.omgservers.module.runtime.impl.service.webService;

import com.omgservers.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.dto.runtime.DoKickClientRequest;
import com.omgservers.dto.runtime.DoKickClientResponse;
import com.omgservers.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.dto.runtime.DoRespondClientRequest;
import com.omgservers.dto.runtime.DoRespondClientResponse;
import com.omgservers.dto.runtime.DoSetAttributesRequest;
import com.omgservers.dto.runtime.DoSetAttributesResponse;
import com.omgservers.dto.runtime.DoSetObjectRequest;
import com.omgservers.dto.runtime.DoSetObjectResponse;
import com.omgservers.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.dto.runtime.DoUnicastMessageResponse;
import com.omgservers.dto.runtime.FindRuntimeGrantRequest;
import com.omgservers.dto.runtime.FindRuntimeGrantResponse;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import com.omgservers.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {
    Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request);

    Uni<SyncRuntimeResponse> syncRuntime(SyncRuntimeRequest request);

    Uni<DeleteRuntimeResponse> deleteRuntime(DeleteRuntimeRequest request);

    Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(ViewRuntimeCommandsRequest request);

    Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(SyncRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(DeleteRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(DeleteRuntimeCommandsRequest request);

    Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(SyncRuntimeGrantRequest request);

    Uni<FindRuntimeGrantResponse> findRuntimeGrant(FindRuntimeGrantRequest request);

    Uni<DeleteRuntimeGrantResponse> deleteRuntimeGrant(DeleteRuntimeGrantRequest request);

    Uni<DoRespondClientResponse> doRespondClient(DoRespondClientRequest request);

    Uni<DoSetAttributesResponse> doSetAttributes(DoSetAttributesRequest request);

    Uni<DoSetObjectResponse> doSetObject(DoSetObjectRequest request);

    Uni<DoKickClientResponse> doKickClient(final DoKickClientRequest request);

    Uni<DoStopRuntimeResponse> doStopRuntime(final DoStopRuntimeRequest request);

    Uni<DoUnicastMessageResponse> doUnicastMessage(final DoUnicastMessageRequest request);

    Uni<DoMulticastMessageResponse> doMulticastMessage(final DoMulticastMessageRequest request);

    Uni<DoBroadcastMessageResponse> doBroadcastMessage(final DoBroadcastMessageRequest request);
}
