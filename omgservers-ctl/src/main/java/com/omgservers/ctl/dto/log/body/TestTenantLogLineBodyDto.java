package com.omgservers.ctl.dto.log.body;

import com.omgservers.ctl.dto.log.LogLineBodyDto;
import com.omgservers.ctl.dto.log.LogLineQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestTenantLogLineBodyDto extends LogLineBodyDto {

    String developer;
    String password;
    String tenant;
    String project;
    String stage;

    @Override
    public LogLineQualifierEnum getQualifier() {
        return LogLineQualifierEnum.TEST_TENANT;
    }
}
