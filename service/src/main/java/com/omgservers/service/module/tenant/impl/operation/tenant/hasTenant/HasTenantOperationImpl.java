package com.omgservers.service.module.tenant.impl.operation.tenant.hasTenant;

import com.omgservers.service.operation.hasObject.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasTenantOperationImpl implements HasTenantOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> hasTenant(final SqlConnection sqlConnection,
                                  final int shard,
                                  final Long id) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_tenant
                        where id = $1 and deleted = false
                        limit 1
                        """,
                List.of(id),
                "Tenant");
    }
}
