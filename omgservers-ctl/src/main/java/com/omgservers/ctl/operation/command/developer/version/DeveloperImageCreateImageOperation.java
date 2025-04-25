package com.omgservers.ctl.operation.command.developer.version;

import com.omgservers.ctl.dto.image.ImageTypeEnum;

import java.net.URI;

public interface DeveloperImageCreateImageOperation {

    void execute(String tenant,
                 String project,
                 String version,
                 ImageTypeEnum type,
                 URI url,
                 String service,
                 String user);
}
