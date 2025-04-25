package com.omgservers.ctl.command;

import com.omgservers.ctl.operation.ctl.GetVersionOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import picocli.CommandLine;


@ApplicationScoped
@AllArgsConstructor
public class CtlVersionProvider implements CommandLine.IVersionProvider {

    final GetVersionOperation getVersionOperation;

    @Override
    public String[] getVersion() {
        final var version = getVersionOperation.execute();
        return new String[] {version};
    }
}
