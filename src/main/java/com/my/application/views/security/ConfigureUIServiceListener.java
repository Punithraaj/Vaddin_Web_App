package com.my.application.views.security;

import com.my.application.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

public class ConfigureUIServiceListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {
            final UI ui = uiInitEvent.getUI();
            ui.addBeforeEnterListener(this::beforeEnter);
        });
    }

    /**
     * Reroutes the user if they are not authorized to access the view
     * @param beforeEnterEvent
     *          before navigation event with event details
     */

    private void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(!LoginView.class.equals(beforeEnterEvent.getNavigationTarget()) &&
                !SecurityUtils.isUserLoggedIn()){
            beforeEnterEvent.rerouteTo(LoginView.class);
        }
    }
}
