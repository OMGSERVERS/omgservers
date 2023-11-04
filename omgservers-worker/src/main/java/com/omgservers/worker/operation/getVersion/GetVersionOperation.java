package com.omgservers.worker.operation.getVersion;

import com.omgservers.model.version.VersionModel;
import io.smallrye.mutiny.Uni;

public interface GetVersionOperation {
    Uni<VersionModel> getVersion();
}
