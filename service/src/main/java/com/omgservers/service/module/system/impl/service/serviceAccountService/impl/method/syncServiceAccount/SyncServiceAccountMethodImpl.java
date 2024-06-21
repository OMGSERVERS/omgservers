package com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.syncServiceAccount;

import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import com.omgservers.service.module.system.impl.operation.serviceAccount.upsertServiceAccount.UpsertServiceAccountOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SyncServiceAccountMethodImpl implements SyncServiceAccountMethod {

    final UpsertServiceAccountOperation syncServiceAccountOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<SyncServiceAccountResponse> syncServiceAccount(final SyncServiceAccountRequest request) {
        log.debug("Sync service account, request={}", request);

        final var serviceAccount = request.getServiceAccount();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        syncServiceAccountOperation.upsertServiceAccount(changeContext, sqlConnection, serviceAccount))
                .map(ChangeContext::getResult)
                .map(SyncServiceAccountResponse::new);
    }
}
