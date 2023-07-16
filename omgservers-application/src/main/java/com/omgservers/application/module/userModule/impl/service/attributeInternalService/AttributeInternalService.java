package com.omgservers.application.module.userModule.impl.service.attributeInternalService;

import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.DeleteAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.GetAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.GetPlayerAttributesInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.SyncAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.DeleteAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetPlayerAttributesInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.SyncAttributeInternalResponse;
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
