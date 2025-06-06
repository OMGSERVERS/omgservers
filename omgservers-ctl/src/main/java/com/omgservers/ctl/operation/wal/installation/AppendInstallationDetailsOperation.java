package com.omgservers.ctl.operation.wal.installation;

import java.net.URI;
import java.nio.file.Path;

public interface AppendInstallationDetailsOperation {

    void execute(Path path, String name, URI address, URI registry);
}
