package com.omgservers.ctl.operation.wal.developer;

import java.nio.file.Path;

public interface AppendDeveloperTokenOperation {

    void execute(Path path,
                 String service,
                 String user,
                 String token);
}
