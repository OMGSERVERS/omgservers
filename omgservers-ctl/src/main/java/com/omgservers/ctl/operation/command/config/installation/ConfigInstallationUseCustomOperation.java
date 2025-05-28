package com.omgservers.ctl.operation.command.config.installation;

import java.net.URI;

public interface ConfigInstallationUseCustomOperation {

    void execute(String name, URI api, URI registry);
}
