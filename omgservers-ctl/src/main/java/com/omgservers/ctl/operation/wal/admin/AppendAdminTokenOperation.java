package com.omgservers.ctl.operation.wal.admin;

import java.nio.file.Path;

public interface AppendAdminTokenOperation {

    void execute(Path path,
                 String service,
                 String user,
                 String token);
}
