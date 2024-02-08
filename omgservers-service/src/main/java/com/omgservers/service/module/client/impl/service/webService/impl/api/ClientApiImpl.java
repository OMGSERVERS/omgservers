package com.omgservers.service.module.client.impl.service.webService.impl.api;

import com.omgservers.model.dto.client.DeleteClientMessagesRequest;
import com.omgservers.model.dto.client.DeleteClientMessagesResponse;
import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.DeleteClientResponse;
import com.omgservers.model.dto.client.DeleteClientRuntimeRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeResponse;
import com.omgservers.model.dto.client.FindClientRuntimeRequest;
import com.omgservers.model.dto.client.FindClientRuntimeResponse;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.GetClientRuntimeRequest;
import com.omgservers.model.dto.client.GetClientRuntimeResponse;
import com.omgservers.model.dto.client.InterchangeRequest;
import com.omgservers.model.dto.client.InterchangeResponse;
import com.omgservers.model.dto.client.SelectClientRuntimeRequest;
import com.omgservers.model.dto.client.SelectClientRuntimeResponse;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientResponse;
import com.omgservers.model.dto.client.SyncClientRuntimeRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeResponse;
import com.omgservers.model.dto.client.ViewClientMessagesRequest;
import com.omgservers.model.dto.client.ViewClientMessagesResponse;
import com.omgservers.model.dto.client.ViewClientRuntimesRequest;
import com.omgservers.model.dto.client.ViewClientRuntimesResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.client.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ClientApiImpl implements ClientApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetClientResponse> getClient(final GetClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncClientResponse> syncClient(final SyncClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteClientResponse> deleteClient(final DeleteClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteClient);
    }

    @Override
    public Uni<InterchangeResponse> interchange(final InterchangeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::interchange);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewClientMessagesResponse> viewClientMessages(final ViewClientMessagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewClientMessages);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncClientMessageResponse> syncClientMessage(final SyncClientMessageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncClientMessage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteClientMessagesResponse> deleteClientMessages(
            final DeleteClientMessagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteClientMessages);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetClientRuntimeResponse> getClientRuntime(final GetClientRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getClientRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindClientRuntimeResponse> findClientRuntime(final FindClientRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findClientRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewClientRuntimesResponse> viewClientRuntimes(final ViewClientRuntimesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewClientRuntimes);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SelectClientRuntimeResponse> selectClientRuntime(final SelectClientRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::selectClientRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncClientRuntimeResponse> syncClientRuntime(final SyncClientRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncClientRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteClientRuntimeResponse> deleteClientRuntime(final DeleteClientRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteClientRuntime);
    }
}
