package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ViewEntityAliasesOperation {
    Uni<List<AliasModel>> execute(AliasQualifierEnum qualifier, String shardKey, Long entityId);
}
