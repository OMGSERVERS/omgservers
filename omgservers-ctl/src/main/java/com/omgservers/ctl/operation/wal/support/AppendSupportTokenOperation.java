package com.omgservers.ctl.operation.wal.support;

import java.nio.file.Path;

public interface AppendSupportTokenOperation {

    void execute(Path path,
                 String service,
                 String user,
                 String token);
}
