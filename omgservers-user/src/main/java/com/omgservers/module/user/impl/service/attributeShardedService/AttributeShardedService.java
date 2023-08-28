package com.omgservers.module.user.impl.service.attributeShardedService;

import com.omgservers.dto.user.DeleteAttributeShardedRequest;
import com.omgservers.dto.user.DeleteAttributeShardedResponse;
import com.omgservers.dto.user.GetAttributeShardedRequest;
import com.omgservers.dto.user.GetAttributeShardedResponse;
import com.omgservers.dto.user.GetPlayerAttributesShardedRequest;
import com.omgservers.dto.user.GetPlayerAttributesShardedResponse;
import com.omgservers.dto.user.SyncAttributeShardedRequest;
import com.omgservers.dto.user.SyncAttributeShardedResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface AttributeShardedService {

    Uni<GetAttributeShardedResponse> getAttribute(GetAttributeShardedRequest request);

    default GetAttributeShardedResponse getAttribute(long timeout, GetAttributeShardedRequest request) {
        return getAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<GetPlayerAttributesShardedResponse> getPlayerAttributes(GetPlayerAttributesShardedRequest request);

    default GetPlayerAttributesShardedResponse getPlayerAttributes(long timeout, GetPlayerAttributesShardedRequest request) {
        return getPlayerAttributes(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<SyncAttributeShardedResponse> syncAttribute(SyncAttributeShardedRequest request);

    default SyncAttributeShardedResponse syncAttribute(long timeout, SyncAttributeShardedRequest request) {
        return syncAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<DeleteAttributeShardedResponse> deleteAttribute(DeleteAttributeShardedRequest request);

    default DeleteAttributeShardedResponse deleteAttribute(long timeout, DeleteAttributeShardedRequest request) {
        return deleteAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
