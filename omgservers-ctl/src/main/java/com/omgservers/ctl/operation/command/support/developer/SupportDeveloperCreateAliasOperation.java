package com.omgservers.ctl.operation.command.support.developer;

public interface SupportDeveloperCreateAliasOperation {

    void execute(Long developerUserId,
                 String alias,
                 String installation);
}
