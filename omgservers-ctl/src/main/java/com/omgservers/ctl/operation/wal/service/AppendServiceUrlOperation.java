package com.omgservers.ctl.operation.wal.service;

import java.net.URI;
import java.nio.file.Path;

public interface AppendServiceUrlOperation {

    void execute(Path path, String name, URI uri);
}
