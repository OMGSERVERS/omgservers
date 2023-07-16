package com.omgservers.application.module.userModule;

import com.omgservers.application.module.userModule.impl.service.attributeInternalService.AttributeInternalService;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.ClientInternalService;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.ObjectInternalService;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.PlayerInternalService;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.PlayerHelpService;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.TokenInternalService;
import com.omgservers.application.module.userModule.impl.service.userInternalService.UserInternalService;
import com.omgservers.application.module.userModule.impl.service.userHelpService.UserHelpService;

public interface UserModule {

    AttributeInternalService getAttributeInternalService();

    ClientInternalService getClientInternalService();

    ObjectInternalService getObjectInternalService();

    PlayerInternalService getPlayerInternalService();

    PlayerHelpService getPlayerHelpService();

    TokenInternalService getTokenInternalService();

    UserInternalService getUserInternalService();

    UserHelpService getUserHelpService();
}
