package org.sleigh.ride.registeruser.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.sleigh.ride.registeruser.domain.User;
import org.sleigh.ride.registeruser.service.RegistrationService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("registration")
@PageTitle("Registration")
@Menu(order = 0, icon = "vaadin:user", title = "Registration")
public class RegistrationView extends Main {

    private final RegistrationService registrationService;

    public RegistrationView(RegistrationService registrationService) {
        this.registrationService = registrationService;

        addClassName(LumoUtility.Padding.MEDIUM);
        setWidthFull();

        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        H2 header = new H2("User Registration");

        TextField userName = new TextField("User Name");
        TextField mobileNumber = new TextField("Mobile Number");
        PasswordField password = new PasswordField("Password");
        PasswordField confirmPassword = new PasswordField("Confirm Password");
        EmailField email = new EmailField("Email");
        TextField address = new TextField("Address");
        ComboBox<String> state = new ComboBox<>("State");
        state.setItems("Maharashtra", "Gujarat", "Karnataka", "Tamil Nadu");

        ComboBox<String> city = new ComboBox<>("City");

        Map<String, List<String>> stateCityMap = new HashMap<>();
        stateCityMap.put("Maharashtra", List.of("Mumbai", "Pune", "Nagpur"));
        stateCityMap.put("Gujarat", List.of("Ahmedabad", "Surat", "Vadodara"));
        stateCityMap.put("Karnataka", List.of("Bangalore", "Mysore"));
        stateCityMap.put("Tamil Nadu", List.of("Chennai", "Coimbatore"));

        state.addValueChangeListener(event -> {
            String selectedState = event.getValue();
            if (selectedState != null) {
                city.setItems(stateCityMap.get(selectedState));
                city.clear();
            } else {
                city.clear();
                city.setItems();
            }
        });

        Button registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        Binder<User> binder = new Binder<>(User.class);
        User user = new User();

        binder.forField(userName).asRequired("enter your full name.").bind(User::getName, User::setName);
        binder.forField(mobileNumber).asRequired("please enter 10 digit mobile number.").bind(User::getMobileNumber, User::setMobileNumber);
        binder.forField(password).asRequired("Enter the password.").bind(User::getPassword, User::setPassword);
        binder.forField(confirmPassword).asRequired("enter same as password.").bind(User::getConfirmedPassword, User::setConfirmedPassword);
        binder.forField(email).asRequired("please enter valid email.").bind(User::getEmailId, User::setEmailId);
        binder.forField(address).asRequired("enter apartment number/building/street name").bind(User::getAddress, User::setAddress);
        binder.forField(state).asRequired("state cannot be empty.").bind(User::getState, User::setState);
        binder.forField(city).asRequired("city cannot be empty.").bind(User::getCity, User::setCity);

        registerButton.addClickListener(e -> {
            try {
                binder.writeBean(user);
                user.setCreationDate(Instant.now());
                registrationService.save(user);
                Notification.show("User registered successfully!", 3000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                binder.readBean(new User());
            } catch (ValidationException ex) {
                Notification.show("Please fill all required fields correctly.");
            }
        });

        FormLayout formLayout = new FormLayout(userName, mobileNumber, password, confirmPassword, email, address, state, city);
        layout.add(header, formLayout, registerButton);
        add(layout);

        Button viewUsersButton = new Button("View Users", e ->
                getUI().ifPresent(ui -> ui.navigate("user-list"))
        );
        viewUsersButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layout.add(viewUsersButton);

        HorizontalLayout buttonLayout = new HorizontalLayout(registerButton, viewUsersButton);
        buttonLayout.setSpacing(true);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.add(buttonLayout);
    }
}
