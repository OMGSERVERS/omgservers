package com.omgservers.ctl.command.support.sub.tenant.sub.util;

import com.omgservers.ctl.dto.permission.TenantPermissionEnum;
import picocli.CommandLine;

public class TenantPermissionConverter implements CommandLine.ITypeConverter<TenantPermissionEnum> {

    @Override
    public TenantPermissionEnum convert(String value) throws Exception {
        return TenantPermissionEnum.fromString(value);
    }
}
