package com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectVersionImageRefsByVersionIdAndQualifier;

import com.omgservers.model.versionImageRef.VersionImageRefModel;
import com.omgservers.model.versionImageRef.VersionImageRefQualifierEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionImageRefsByVersionIdAndQualifier {
    Uni<VersionImageRefModel> selectVersionImageRefsByVersionIdAndQualifier(
            SqlConnection sqlConnection,
            int shard,
            Long tenantId,
            Long versionId,
            VersionImageRefQualifierEnum qualifier);
}
