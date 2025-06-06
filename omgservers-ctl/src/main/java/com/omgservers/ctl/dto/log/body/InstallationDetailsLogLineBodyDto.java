package com.omgservers.ctl.dto.log.body;

import com.omgservers.ctl.dto.log.LogLineBodyDto;
import com.omgservers.ctl.dto.log.LogLineQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InstallationDetailsLogLineBodyDto extends LogLineBodyDto {

    String name;
    URI address;
    URI registry;

    @Override
    public LogLineQualifierEnum getQualifier() {
        return LogLineQualifierEnum.INSTALLATION_DETAILS;
    }
}
