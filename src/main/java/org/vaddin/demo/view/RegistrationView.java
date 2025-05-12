package org.vaddin.demo.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaddin.demo.common.base.BaseView;
import org.vaddin.demo.model.User;
import org.vaddin.demo.presenter.RegistrationPresenter;

import java.util.List;
import java.util.Map;

@Route(value = "registration", layout = NavBarView.class)
@PageTitle("Registration")
public class RegistrationView extends BaseView<RegistrationPresenter> {

    private Binder<User> binder;
    private User user;
    private TextField userName;
    private TextField mobileNumber;
    private TextField address;
    private PasswordField password;
    private PasswordField confirmPassword;
    private EmailField email;
    private ComboBox<String> state;
    private ComboBox<String> city;
    private Map<String, List<String>> stateCityMap;

    @Override
    public void init() {
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setSizeFull();
        H2 header = new H2("User Registration");

        initFormFields();
        setupStateCityLogic();
        setupBinding();

        FormLayout formLayout = new FormLayout(userName, mobileNumber, password, confirmPassword, email, address, state, city);
        HorizontalLayout buttonLayout = createButtons();

        add(header, formLayout, buttonLayout);
    }

    private void initFormFields() {
        userName = new TextField("User Name");
        mobileNumber = new TextField("Mobile Number");
        password = new PasswordField("Password");
        confirmPassword = new PasswordField("Confirm Password");
        email = new EmailField("Email");
        address = new TextField("Address");
        state = new ComboBox<>("State");
        city = new ComboBox<>("City");

        state.setItems(getPresenter().getStates());
        stateCityMap = getPresenter().getStateCityMap();
    }

    private void setupStateCityLogic() {
        state.addValueChangeListener(event -> {
            String selected = event.getValue();
            city.setItems(selected != null ? stateCityMap.get(selected) : List.of());
            city.clear();
        });
    }

    private void setupBinding() {
        binder = new Binder<>(User.class);
        user = new User();

        binder.forField(userName).asRequired("Enter your full name.").bind(User::getName, User::setName);
        binder.forField(mobileNumber).asRequired("Please enter 10 digit mobile number.").bind(User::getMobileNumber, User::setMobileNumber);
        binder.forField(password).asRequired("Enter the password.").bind(User::getPassword, User::setPassword);
        binder.forField(confirmPassword).asRequired("Enter same as password.")
                .withValidator(confirm -> confirm.equals(password.getValue()), "Passwords do not match")
                .bind(User::getConfirmedPassword, User::setConfirmedPassword);
        binder.forField(email).asRequired("Please enter valid email.")
                .withValidator(emailVal -> emailVal != null && emailVal.contains("@"), "Invalid email address")
                .bind(User::getEmailId, User::setEmailId);
        binder.forField(address).asRequired("Enter apartment number/building/street name").bind(User::getAddress, User::setAddress);
        binder.forField(state).asRequired("State cannot be empty.").bind(User::getState, User::setState);
        binder.forField(city).asRequired("City cannot be empty.").bind(User::getCity, User::setCity);
    }

    private HorizontalLayout createButtons() {
        Button registerButton = new Button("Register", e -> handleRegistration());
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        Button viewUsersButton = new Button("View Users", e -> getUI().ifPresent(ui -> ui.navigate("user-list")));
        viewUsersButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout layout = new HorizontalLayout(registerButton, viewUsersButton);
        layout.setSpacing(true);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return layout;
    }

    private void handleRegistration() {
        try {
            binder.writeBean(user);
            getPresenter().registerUser(user);
            Notification.show("User registered successfully!", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            binder.readBean(new User());
        } catch (ValidationException ex) {
            Notification.show("Please fill all required fields correctly.");
        }
    }
}