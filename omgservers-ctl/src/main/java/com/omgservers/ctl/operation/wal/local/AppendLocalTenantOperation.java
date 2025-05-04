package com.omgservers.ctl.operation.wal.local;

import java.nio.file.Path;

public interface AppendLocalTenantOperation {

    void execute(Path path,
                 String developer,
                 String password,
                 String tenant,
                 String project,
                 String stage);
}
