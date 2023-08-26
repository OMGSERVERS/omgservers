package com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi;

import com.omgservers.application.module.userModule.impl.service.userWebService.UserWebService;
import com.omgservers.base.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.userModule.CreateTokenRoutedRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
import com.omgservers.dto.userModule.DeleteAttributeRoutedRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.DeleteClientRoutedRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import com.omgservers.dto.userModule.DeleteObjectRoutedRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import com.omgservers.dto.userModule.DeletePlayerRoutedRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetAttributeRoutedRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetClientRoutedRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.dto.userModule.GetObjectRoutedRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.GetPlayerRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.IntrospectTokenInternalRequest;
import com.omgservers.dto.userModule.IntrospectTokenInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeRoutedRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import com.omgservers.dto.userModule.SyncClientRoutedRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import com.omgservers.dto.userModule.SyncObjectRoutedRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerRoutedRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncUserRoutedRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsRoutedRequest;
import com.omgservers.dto.userModule.ValidateCredentialsInternalResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UserServiceApiImpl implements UserServiceApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final UserWebService userWebService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncUserInternalResponse> syncUser(final SyncUserRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncUser);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::validateCredentials);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<CreateTokenInternalResponse> createToken(final CreateTokenRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::createToken);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<IntrospectTokenInternalResponse> introspectToken(final IntrospectTokenInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::introspectToken);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetPlayerInternalResponse> getPlayer(GetPlayerRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deletePlayer);
    }

    @Override
    public Uni<SyncClientInternalResponse> syncClient(SyncClientRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetClientInternalResponse> getClient(GetClientRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteClientInternalResponse> deleteClient(DeleteClientRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetAttributeInternalResponse> getAttribute(GetAttributeRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getPlayerAttributes);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetObjectInternalResponse> getObject(GetObjectRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getObject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncObjectInternalResponse> syncObject(SyncObjectRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncObject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteObject);
    }
}
