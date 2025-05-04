package com.omgservers.ctl.dto.log;

import com.omgservers.ctl.dto.log.body.AdminTokenLogLineBodyDto;
import com.omgservers.ctl.dto.log.body.DeveloperTokenLogLineBodyDto;
import com.omgservers.ctl.dto.log.body.InstallationDetailsLogLineBodyDto;
import com.omgservers.ctl.dto.log.body.LocalTenantLogLineBodyDto;
import com.omgservers.ctl.dto.log.body.ResultMapLogLineBodyDto;
import com.omgservers.ctl.dto.log.body.SupportTokenLogLineBodyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogLineQualifierEnum {
    RESULT_MAP(ResultMapLogLineBodyDto.class),
    INSTALLATION_DETAILS(InstallationDetailsLogLineBodyDto.class),
    ADMIN_TOKEN(AdminTokenLogLineBodyDto.class),
    SUPPORT_TOKEN(SupportTokenLogLineBodyDto.class),
    DEVELOPER_TOKEN(DeveloperTokenLogLineBodyDto.class),
    LOCAL_TENANT(LocalTenantLogLineBodyDto.class);

    final Class<? extends LogLineBodyDto> bodyClass;
}
