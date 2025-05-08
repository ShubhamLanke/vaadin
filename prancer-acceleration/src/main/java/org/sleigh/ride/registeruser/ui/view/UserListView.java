package org.sleigh.ride.registeruser.ui.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.sleigh.ride.registeruser.domain.User;
import org.sleigh.ride.registeruser.service.RegistrationService;

@Route("user-list")
@PageTitle("Registered Users")
@Menu(order = 1, icon = "vaadin:table", title = "User List")
public class UserListView extends VerticalLayout {

    public UserListView(RegistrationService registrationService) {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        H2 heading = new H2("Registered Users");

        Grid<User> userGrid = new Grid<>(User.class);
        userGrid.setColumns("name", "mobileNumber", "emailId", "address", "state", "city", "creationDate");
        userGrid.setItems(registrationService.findAll());

        add(heading, userGrid);
    }
}
