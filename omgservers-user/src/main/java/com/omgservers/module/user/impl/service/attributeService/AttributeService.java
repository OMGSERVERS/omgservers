package com.omgservers.module.user.impl.service.attributeService;

import com.omgservers.dto.user.DeleteAttributeRequest;
import com.omgservers.dto.user.DeleteAttributeResponse;
import com.omgservers.dto.user.GetAttributeRequest;
import com.omgservers.dto.user.GetAttributeResponse;
import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.dto.user.SyncAttributeRequest;
import com.omgservers.dto.user.SyncAttributeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

import java.time.Duration;

public interface AttributeService {

    Uni<GetAttributeResponse> getAttribute(@Valid GetAttributeRequest request);

    default GetAttributeResponse getAttribute(long timeout, GetAttributeRequest request) {
        return getAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<GetPlayerAttributesResponse> getPlayerAttributes(@Valid GetPlayerAttributesRequest request);

    default GetPlayerAttributesResponse getPlayerAttributes(long timeout, GetPlayerAttributesRequest request) {
        return getPlayerAttributes(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<SyncAttributeResponse> syncAttribute(@Valid SyncAttributeRequest request);

    default SyncAttributeResponse syncAttribute(long timeout, SyncAttributeRequest request) {
        return syncAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<DeleteAttributeResponse> deleteAttribute(@Valid DeleteAttributeRequest request);

    default DeleteAttributeResponse deleteAttribute(long timeout, DeleteAttributeRequest request) {
        return deleteAttribute(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
