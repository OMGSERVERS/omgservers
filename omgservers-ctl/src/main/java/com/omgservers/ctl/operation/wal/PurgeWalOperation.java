package com.omgservers.ctl.operation.wal;

import java.nio.file.Path;

public interface PurgeWalOperation {

    void execute(Path path);
}
