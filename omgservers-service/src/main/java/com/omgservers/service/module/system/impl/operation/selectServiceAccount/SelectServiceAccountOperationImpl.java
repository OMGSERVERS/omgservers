package com.omgservers.service.module.system.impl.operation.selectServiceAccount;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import com.omgservers.service.module.system.impl.mappers.ServiceAccountModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectServiceAccountOperationImpl implements SelectServiceAccountOperation {

    final SelectObjectOperation selectObjectOperation;

    final ServiceAccountModelMapper serviceAccountModelMapper;

    @Override
    public Uni<ServiceAccountModel> selectServiceAccount(final SqlConnection sqlConnection,
                                                         final Long id,
                                                         final Boolean deleted) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select id, created, modified, username, password_hash, deleted
                        from system.tab_service_account
                        where id = $1 and deleted = $2
                        limit 1
                        """,
                Arrays.asList(id, deleted),
                "Service account",
                serviceAccountModelMapper::fromRow);
    }
}
