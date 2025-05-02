package com.omgservers.ctl.operation.wal.localtesting;

import java.nio.file.Path;

public interface AppendTestTenantOperation {

    void execute(Path path,
                 String developer,
                 String password,
                 String tenant,
                 String project,
                 String stage);
}
