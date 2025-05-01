package com.omgservers.ctl.operation.command.developer;

public interface DeveloperCreateTokenOperation {

    void execute(String user, String password, String service);
}
