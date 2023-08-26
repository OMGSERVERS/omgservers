package com.omgservers.application.module.userModule.impl.service.attributeInternalService;

import com.omgservers.dto.userModule.DeleteAttributeShardRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.GetAttributeShardRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesShardRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeShardRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface AttributeInternalService {

    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeShardRequest request);

    default GetAttributeInternalResponse getAttribute(long timeout, GetAttributeShardRequest request) {
        return getAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesShardRequest request);

    default GetPlayerAttributesInternalResponse getPlayerAttributes(long timeout, GetPlayerAttributesShardRequest request) {
        return getPlayerAttributes(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeShardRequest request);

    default SyncAttributeInternalResponse syncAttribute(long timeout, SyncAttributeShardRequest request) {
        return syncAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeShardRequest request);

    default DeleteAttributeInternalResponse deleteAttribute(long timeout, DeleteAttributeShardRequest request) {
        return deleteAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
