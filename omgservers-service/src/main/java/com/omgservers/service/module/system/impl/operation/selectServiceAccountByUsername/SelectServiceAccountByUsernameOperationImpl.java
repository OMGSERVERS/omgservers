package com.omgservers.service.module.system.impl.operation.selectServiceAccountByUsername;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import com.omgservers.service.module.system.impl.mappers.ServiceAccountModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectServiceAccountByUsernameOperationImpl implements SelectServiceAccountByUsernameOperation {

    final SelectObjectOperation selectObjectOperation;

    final ServiceAccountModelMapper serviceAccountModelMapper;

    @Override
    public Uni<ServiceAccountModel> selectServiceAccountByUsername(final SqlConnection sqlConnection,
                                                                   final String username) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select id, created, modified, username, password_hash, deleted
                        from system.tab_service_account
                        where username = $1
                        limit 1
                        """,
                List.of(username),
                "Service account",
                serviceAccountModelMapper::fromRow);
    }
}
