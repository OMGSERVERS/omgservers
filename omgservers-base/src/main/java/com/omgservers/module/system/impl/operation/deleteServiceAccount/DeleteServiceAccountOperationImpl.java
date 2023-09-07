package com.omgservers.module.system.impl.operation.deleteServiceAccount;

import com.omgservers.model.event.body.ServiceAccountDeletedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
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

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteServiceAccount(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final Long id) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, 0,
                """
                        delete from internal.tab_service_account
                        where id = $1
                        """,
                Collections.singletonList(id),
                () -> new ServiceAccountDeletedEventBodyModel(id),
                () -> logModelFactory.create("Service account was deleted, id=" + id)
        );
    }
}
