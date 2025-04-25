package com.omgservers.ctl.command.config.sub;

import com.omgservers.ctl.command.config.sub.converter.RegionCandidates;
import com.omgservers.ctl.command.config.sub.converter.RegionConverter;
import com.omgservers.ctl.dto.region.RegionEnum;
import com.omgservers.ctl.operation.command.service.ServiceUseCloudOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "use-cloud",
        description = "Use the OMGSERVERS cloud service.")
public class ServiceUseCloudCommand implements Runnable {

    @CommandLine.Parameters(description = "Region name. Possible values: ${COMPLETION-CANDIDATES}.",
            converter = RegionConverter.class,
            completionCandidates = RegionCandidates.class)
    RegionEnum region;

    @Inject
    ServiceUseCloudOperation serviceUseCloudOperation;

    @Override
    public void run() {
        serviceUseCloudOperation.execute(region);
    }
}
