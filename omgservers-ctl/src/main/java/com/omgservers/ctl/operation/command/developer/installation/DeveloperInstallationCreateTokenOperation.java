package com.omgservers.ctl.operation.command.developer.installation;

public interface DeveloperInstallationCreateTokenOperation {

    void execute(String user, String password, String service);
}
