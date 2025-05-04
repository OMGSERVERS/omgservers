package com.omgservers.ctl.command.ctl.sub.installation.sub;

import com.omgservers.ctl.command.ctl.sub.installation.sub.util.RegionCandidates;
import com.omgservers.ctl.command.ctl.sub.installation.sub.util.RegionConverter;
import com.omgservers.ctl.dto.region.RegionEnum;
import com.omgservers.ctl.operation.command.ctl.installation.CtlInstallationUseCloudOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "use-cloud",
        description = "Use the OMGSERVERS cloud service installation.")
public class CtlInstallationUseCloudCommand implements Runnable {

    @CommandLine.Parameters(description = "Region name. Possible values: ${COMPLETION-CANDIDATES}.",
            converter = RegionConverter.class,
            completionCandidates = RegionCandidates.class)
    RegionEnum region;

    @Inject
    CtlInstallationUseCloudOperation ctlInstallationUseCloudOperation;

    @Override
    public void run() {
        ctlInstallationUseCloudOperation.execute(region);
    }
}
