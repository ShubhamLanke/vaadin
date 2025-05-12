package org.vaddin.demo.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaddin.demo.common.base.BaseView;
import org.vaddin.demo.model.User;
import org.vaddin.demo.presenter.UserListPresenter;
import org.vaddin.demo.service.RegistrationService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Route(value = "user-list", layout = NavBarView.class)
@PageTitle("Registered User's List")
public class UserListView extends BaseView<UserListPresenter> {

    private final RegistrationService registrationService;

    public UserListView(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void init() {
        setSizeFull();
        getStyle().set("padding", "var(--lumo-space-m)");

        H2 header = new H2("User List");

        List<User> users = registrationService.getAllUsers();
        ListDataProvider<User> dataProvider = new ListDataProvider<>(users);

        Grid<User> userGrid = createUserGrid(dataProvider);

        Button save = new Button("Save", e -> userGrid.getEditor().save());
        Button cancel = new Button("Cancel", e -> userGrid.getEditor().cancel());
        HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel);

        userGrid.getEditor().addSaveListener(event -> {
            User updatedUser = event.getItem();
            registrationService.updateUser(updatedUser);
        });

        add(header, userGrid, buttonLayout);
    }

    private Grid<User> createUserGrid(ListDataProvider<User> dataProvider) {
        Grid<User> grid = new Grid<>();
        grid.setItems(dataProvider);
        grid.setSizeFull();

        Binder<User> binder = new Binder<>(User.class);
        Editor<User> editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        addEditableColumn(grid, binder, "Name", User::getName, User::setName);
        addEditableColumn(grid, binder, "Email ID", User::getEmailId, User::setEmailId);
        addEditableColumn(grid, binder, "Mobile", User::getMobileNumber, User::setMobileNumber);
        addEditableColumn(grid, binder, "City", User::getCity, User::setCity);

        grid.addColumn(User::getState).setHeader("State").setAutoWidth(true);
        grid.addColumn(User::getAddress).setHeader("Address").setAutoWidth(true);
        grid.addColumn(User::getCreationDate).setHeader("Created On").setAutoWidth(true);

        addDeleteColumn(grid, dataProvider);

        grid.addItemDoubleClickListener(e -> {
            if (editor.isOpen()) {
                editor.cancel();
            }
            editor.editItem(e.getItem());

            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable<?>) {
                ((Focusable<?>) editorComponent).focus();
            }
        });

        return grid;
    }

    private void addEditableColumn(Grid<User> grid, Binder<User> binder, String header,
                                   Function<User, String> getter, Setter<User, String> setter) {
        Grid.Column<User> column = grid.addColumn(user ->
                Optional.ofNullable(getter.apply(user)).orElse("")
        ).setHeader(header).setAutoWidth(true).setResizable(true);

        TextField editorField = new TextField();
        binder.forField(editorField).bind(getter::apply, setter);
        column.setEditorComponent(editorField);
    }

    private void addDeleteColumn(Grid<User> grid, ListDataProvider<User> dataProvider) {
        grid.addColumn(new ComponentRenderer<>(user -> {
            Button deleteButton = new Button("Delete", event -> {
                Dialog confirmDialog = new Dialog();
                confirmDialog.setHeaderTitle("Confirm Delete");

                confirmDialog.add(new Span(
                        "Are you sure you want to delete user: " + user.getName() + "?"));

                Button confirm = new Button("Delete", e -> {
                    dataProvider.getItems().remove(user);
                    dataProvider.refreshAll();
                    registrationService.deleteUser(user);
                    confirmDialog.close();
                });
                confirm.getStyle().set("color", "white");
                confirm.getStyle().set("background-color", "red");

                Button cancel = new Button("Cancel", e -> confirmDialog.close());

                HorizontalLayout buttons = new HorizontalLayout(confirm, cancel);
                confirmDialog.getFooter().add(buttons);

                confirmDialog.open();
            });

            deleteButton.getStyle().set("color", "red");
            return deleteButton;
        })).setHeader("Actions").setAutoWidth(true);
    }
}
