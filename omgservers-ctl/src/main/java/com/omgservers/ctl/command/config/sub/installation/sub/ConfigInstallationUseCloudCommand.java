package com.omgservers.ctl.command.config.sub.installation.sub;

import com.omgservers.ctl.command.config.sub.installation.sub.util.RegionCandidates;
import com.omgservers.ctl.command.config.sub.installation.sub.util.RegionConverter;
import com.omgservers.ctl.dto.region.RegionEnum;
import com.omgservers.ctl.operation.command.config.installation.ConfigInstallationUseCloudOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "use-cloud",
        description = "Use the OMGSERVERS cloud service installation.")
public class ConfigInstallationUseCloudCommand implements Runnable {

    @CommandLine.Parameters(description = "Region name. Possible values: ${COMPLETION-CANDIDATES}.",
            converter = RegionConverter.class,
            completionCandidates = RegionCandidates.class)
    RegionEnum region;

    @Inject
    ConfigInstallationUseCloudOperation configInstallationUseCloudOperation;

    @Override
    public void run() {
        configInstallationUseCloudOperation.execute(region);
    }
}
