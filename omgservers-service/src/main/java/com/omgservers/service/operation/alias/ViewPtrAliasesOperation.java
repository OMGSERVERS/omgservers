package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ViewPtrAliasesOperation {
    Uni<List<AliasModel>> execute(Long entityId);
}
