package com.omgservers.service.module.tenant.impl.operation.hasVersion;

import com.omgservers.service.operation.hasObject.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasVersionOperationImpl implements HasVersionOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> hasVersion(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long tenantId,
                                   final Long id) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_tenant_version
                        where tenant_id = $1 and id = $2 and deleted = false
                        limit 1
                        """,
                Arrays.asList(tenantId, id),
                "Version");
    }
}
