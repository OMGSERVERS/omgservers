package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteTenant;

import com.omgservers.model.dto.support.DeleteTenantSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMethod {
    Uni<DeleteTenantSupportResponse> deleteTenant(DeleteTenantSupportRequest request);

}
