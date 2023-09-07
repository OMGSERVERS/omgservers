package com.omgservers.module.user.impl.service.attributeService.impl.method.deleteAttribute;

import com.omgservers.dto.user.DeleteAttributeResponse;
import com.omgservers.dto.user.DeleteAttributeRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteAttributeMethod {
    Uni<DeleteAttributeResponse> deleteAttribute(DeleteAttributeRequest request);
}
