package com.omgservers.module.system.impl.operation.deleteServiceAccount;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.ServiceAccountDeletedEventBodyModel;
import com.omgservers.module.system.impl.operation.selectServiceAccount.SelectServiceAccountOperation;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteServiceAccountOperationImpl implements DeleteServiceAccountOperation {

    final SelectServiceAccountOperation selectServiceAccountOperation;
    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteServiceAccount(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final String username) {
        return selectServiceAccountOperation.selectServiceAccount(sqlConnection, username)
                .flatMap(serviceAccount -> changeObjectOperation.changeObject(
                        changeContext, sqlConnection, 0,
                        """
                                delete from system.tab_service_account
                                where username = $1
                                """,
                        Collections.singletonList(username),
                        () -> new ServiceAccountDeletedEventBodyModel(serviceAccount),
                        () -> logModelFactory.create("Service account was deleted, serviceAccount=" + serviceAccount)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
