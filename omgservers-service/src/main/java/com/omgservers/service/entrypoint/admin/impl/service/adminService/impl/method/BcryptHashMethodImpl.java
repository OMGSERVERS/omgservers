package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method;

import com.omgservers.schema.entrypoint.admin.BcryptHashAdminRequest;
import com.omgservers.schema.entrypoint.admin.BcryptHashAdminResponse;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BcryptHashMethodImpl implements BcryptHashMethod {

    final GenerateIdOperation generateIdOperation;

    @Override
    public Uni<BcryptHashAdminResponse> execute(final BcryptHashAdminRequest request) {
        log.trace("{}", request);
        return Uni.createFrom().item(BcryptUtil.bcryptHash(request.getValue()))
                .map(BcryptHashAdminResponse::new);
    }
}
