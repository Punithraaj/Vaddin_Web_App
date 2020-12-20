package com.my.application.views.myapp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.my.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "default", layout = MainView.class)
@PageTitle("MyApp")
@CssImport("./styles/views/myapp/my-app-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class MyAppView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public MyAppView() {
        setId("my-app-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
