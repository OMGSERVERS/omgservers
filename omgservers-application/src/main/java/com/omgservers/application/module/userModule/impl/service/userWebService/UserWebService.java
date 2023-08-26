package com.omgservers.application.module.userModule.impl.service.userWebService;

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
import io.smallrye.mutiny.Uni;

public interface UserWebService {

    Uni<SyncUserInternalResponse> syncUser(SyncUserRoutedRequest request);

    Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsRoutedRequest request);

    Uni<CreateTokenInternalResponse> createToken(CreateTokenRoutedRequest request);

    Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request);

    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerRoutedRequest request);

    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerRoutedRequest request);

    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerRoutedRequest request);

    Uni<SyncClientInternalResponse> syncClient(SyncClientRoutedRequest request);

    Uni<GetClientInternalResponse> getClient(GetClientRoutedRequest request);

    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientRoutedRequest request);

    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeRoutedRequest request);

    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesRoutedRequest request);

    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeRoutedRequest request);

    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeRoutedRequest request);

    Uni<GetObjectInternalResponse> getObject(GetObjectRoutedRequest request);

    Uni<SyncObjectInternalResponse> syncObject(SyncObjectRoutedRequest request);

    Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectRoutedRequest request);
}
