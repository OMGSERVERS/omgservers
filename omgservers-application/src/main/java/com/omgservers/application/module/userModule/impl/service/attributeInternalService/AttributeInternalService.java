package com.omgservers.application.module.userModule.impl.service.attributeInternalService;

import com.omgservers.dto.userModule.DeleteAttributeInternalRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.GetAttributeInternalRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeInternalRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface AttributeInternalService {

    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeInternalRequest request);

    default GetAttributeInternalResponse getAttribute(long timeout, GetAttributeInternalRequest request) {
        return getAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesInternalRequest request);

    default GetPlayerAttributesInternalResponse getPlayerAttributes(long timeout, GetPlayerAttributesInternalRequest request) {
        return getPlayerAttributes(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeInternalRequest request);

    default SyncAttributeInternalResponse syncAttribute(long timeout, SyncAttributeInternalRequest request) {
        return syncAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeInternalRequest request);

    default DeleteAttributeInternalResponse deleteAttribute(long timeout, DeleteAttributeInternalRequest request) {
        return deleteAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
