package com.omgservers.service.module.system.impl.operation.deleteServiceAccount;

import com.omgservers.model.event.body.system.ServiceAccountDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.system.impl.operation.selectServiceAccountByUsername.SelectServiceAccountByUsernameOperation;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteServiceAccountOperationImpl implements DeleteServiceAccountOperation {

    final SelectServiceAccountByUsernameOperation selectServiceAccountByUsernameOperation;
    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteServiceAccount(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        update $schema.tab_service_account
                        set modified = $2, deleted = true
                        where id = $1 and deleted = false
                        """,
                List.of(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new ServiceAccountDeletedEventBodyModel(id),
                () -> null
        );
    }
}
