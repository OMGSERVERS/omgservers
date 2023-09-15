package com.omgservers.module.system.impl.service.serviceAccountService.impl.method.deleteServiceAccount;

import com.omgservers.dto.internal.DeleteServiceAccountRequest;
import com.omgservers.module.system.impl.operation.deleteServiceAccount.DeleteServiceAccountOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteServiceAccountMethodImpl implements DeleteServiceAccountMethod {

    final DeleteServiceAccountOperation deleteServiceAccountOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<Void> deleteServiceAccount(DeleteServiceAccountRequest request) {
        final var username = request.getUsername();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteServiceAccountOperation.deleteServiceAccount(changeContext, sqlConnection, username))
                .map(ChangeContext::getResult)
                //TODO: make response with deleted flag
                .replaceWithVoid();
    }
}
