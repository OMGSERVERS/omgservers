package com.omgservers.ctl.operation.command.admin;

public interface AdminCreateTokenOperation {

    void execute(String user, String password, String service);
}
