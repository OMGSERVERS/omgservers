package com.omgservers.application.module.userModule.impl.service.attributeInternalService;

import com.omgservers.dto.userModule.DeleteAttributeRoutedRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.GetAttributeRoutedRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeRoutedRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface AttributeInternalService {

    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeRoutedRequest request);

    default GetAttributeInternalResponse getAttribute(long timeout, GetAttributeRoutedRequest request) {
        return getAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesRoutedRequest request);

    default GetPlayerAttributesInternalResponse getPlayerAttributes(long timeout, GetPlayerAttributesRoutedRequest request) {
        return getPlayerAttributes(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeRoutedRequest request);

    default SyncAttributeInternalResponse syncAttribute(long timeout, SyncAttributeRoutedRequest request) {
        return syncAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeRoutedRequest request);

    default DeleteAttributeInternalResponse deleteAttribute(long timeout, DeleteAttributeRoutedRequest request) {
        return deleteAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
