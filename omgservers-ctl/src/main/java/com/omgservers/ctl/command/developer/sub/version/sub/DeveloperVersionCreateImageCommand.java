package com.omgservers.ctl.command.developer.sub.version.sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.command.developer.sub.version.sub.util.ImageTypeCandidates;
import com.omgservers.ctl.command.developer.sub.version.sub.util.ImageTypeConverter;
import com.omgservers.ctl.dto.image.ImageTypeEnum;
import com.omgservers.ctl.operation.command.developer.version.DeveloperImageCreateImageOperation;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.net.URI;

@Slf4j
@CommandLine.Command(
        name = "create-image",
        description = "Create a new image.")
public class DeveloperVersionCreateImageCommand extends UserCommand {


    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project that owns the version.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the version for which the image will be created.")
    String version;

    @CommandLine.Parameters(description = "Type of image. Possible values: ${COMPLETION-CANDIDATES}.",
            converter = ImageTypeConverter.class,
            completionCandidates = ImageTypeCandidates.class)
    ImageTypeEnum type;

    @CommandLine.Parameters(description = "Image url.")
    URI url;

    @Inject
    DeveloperImageCreateImageOperation developerImageCreateImageOperation;

    @Inject
    ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void run() {
        developerImageCreateImageOperation.execute(tenant,
                project,
                version,
                type,
                url,
                service,
                user);
    }
}
