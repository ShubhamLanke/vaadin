package org.sleigh.ride.base.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Layout;
import org.sleigh.ride.base.ui.component.ViewToolbar;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.sleigh.ride.registeruser.ui.view.LoginView;
import org.sleigh.ride.registeruser.ui.view.RegistrationView;

/**
 * This view shows up when a user navigates to the root ('/') of the application.
 */
@Route
public final class MainView extends Main {

    // TODO Replace with your own main view.

    VerticalLayout layout = new VerticalLayout();

    MainView() {
        addClassName(LumoUtility.Padding.MEDIUM);
        add(new ViewToolbar("Main"));
        add(new Div("Please select a view from the menu on the left."));

        Button loginButton = new Button("Login");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);
        loginButton.addClickListener(e -> UI.getCurrent().navigate(LoginView.class));
        layout.add(loginButton);
        add(layout);

        Button registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClickListener(e -> UI.getCurrent().navigate(RegistrationView.class));
        layout.add(registerButton);
        add(layout);

        HorizontalLayout buttonLayout = new HorizontalLayout(registerButton, loginButton);
        buttonLayout.setSpacing(true);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.add(buttonLayout);
    }

    /**
     * Navigates to the main view.
     */
    public static void showMainView() {
        UI.getCurrent().navigate(MainView.class);
    }
}
