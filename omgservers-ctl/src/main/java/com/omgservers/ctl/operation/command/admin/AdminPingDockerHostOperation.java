package com.omgservers.ctl.operation.command.admin;

import java.net.URI;

public interface AdminPingDockerHostOperation {

    void execute(URI dockerDaemonUri, String service, String user);
}
