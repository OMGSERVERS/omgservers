package com.omgservers.service.module.runtime;

import com.omgservers.service.module.runtime.impl.service.doService.DoService;
import com.omgservers.service.module.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.module.runtime.impl.service.shortcutService.ShortcutService;

public interface RuntimeModule {

    RuntimeService getRuntimeService();

    DoService getDoService();

    ShortcutService getShortcutService();
}
