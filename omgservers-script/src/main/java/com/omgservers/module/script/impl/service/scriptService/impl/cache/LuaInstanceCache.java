package com.omgservers.module.script.impl.service.scriptService.impl.cache;

import com.omgservers.base.cache.InMemoryCache;
import com.omgservers.module.script.impl.operation.getLuaInstance.LuaInstance;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LuaInstanceCache extends InMemoryCache<Long, LuaInstance> {

}
