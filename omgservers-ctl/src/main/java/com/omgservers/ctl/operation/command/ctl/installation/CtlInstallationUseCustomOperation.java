package com.omgservers.ctl.operation.command.ctl.installation;

import java.net.URI;

public interface CtlInstallationUseCustomOperation {

    void execute(String name, URI api, URI registry);
}
