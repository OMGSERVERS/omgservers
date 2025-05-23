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
public class AdminTokenLogLineBodyDto extends LogLineBodyDto {

    String installation;
    String user;
    String token;

    @Override
    public LogLineQualifierEnum getQualifier() {
        return LogLineQualifierEnum.ADMIN_TOKEN;
    }
}
