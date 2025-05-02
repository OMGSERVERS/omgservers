package com.omgservers.ctl.dto.log;

import com.omgservers.ctl.dto.log.body.AdminTokenLogLineBodyDto;
import com.omgservers.ctl.dto.log.body.DeveloperTokenLogLineBodyDto;
import com.omgservers.ctl.dto.log.body.ResultMapLogLineBodyDto;
import com.omgservers.ctl.dto.log.body.ServiceUrlLogLineBodyDto;
import com.omgservers.ctl.dto.log.body.SupportTokenLogLineBodyDto;
import com.omgservers.ctl.dto.log.body.TestTenantLogLineBodyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogLineQualifierEnum {
    RESULT_MAP(ResultMapLogLineBodyDto.class),
    SERVICE_URL(ServiceUrlLogLineBodyDto.class),
    ADMIN_TOKEN(AdminTokenLogLineBodyDto.class),
    SUPPORT_TOKEN(SupportTokenLogLineBodyDto.class),
    DEVELOPER_TOKEN(DeveloperTokenLogLineBodyDto.class),
    TEST_TENANT(TestTenantLogLineBodyDto.class);

    final Class<? extends LogLineBodyDto> bodyClass;
}
