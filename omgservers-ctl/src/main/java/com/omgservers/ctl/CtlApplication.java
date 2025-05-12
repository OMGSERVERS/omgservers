package com.omgservers.ctl;

import com.omgservers.ctl.command.CtlVersionProvider;
import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.admin.AdminCommand;
import com.omgservers.ctl.command.ctl.CtlCommand;
import com.omgservers.ctl.command.developer.DeveloperCommand;
import com.omgservers.ctl.command.get.GetCommand;
import com.omgservers.ctl.command.support.SupportCommand;
import com.omgservers.ctl.exception.ExceptionHandler;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import picocli.CommandLine;

@QuarkusMain
@CommandLine.Command(
        name = "omgserversctl",
        versionProvider = CtlVersionProvider.class,
        mixinStandardHelpOptions = true,
        subcommands = {
                GetCommand.class,
                CtlCommand.class,
                AdminCommand.class,
                SupportCommand.class,
                DeveloperCommand.class,
        }
)
public class CtlApplication extends ParentCommand implements QuarkusApplication {

    @Inject
    CommandLine.IFactory factory;

    public static void main(final String... args) {
        Quarkus.run(CtlApplication.class, args);
    }

    @Override
    public int run(final String... args) throws Exception {
        final var commandLine = new CommandLine(this, factory)
                .setExecutionExceptionHandler(new ExceptionHandler())
                .execute(args);
        return commandLine;
    }
}
