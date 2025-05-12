package org.vaddin.demo.presenter;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.vaddin.demo.common.base.BasePresenter;
import org.vaddin.demo.model.User;
import org.vaddin.demo.service.RegistrationService;
import org.vaddin.demo.view.RegistrationView;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@UIScope
@SpringComponent
public class RegistrationPresenter extends BasePresenter<RegistrationView> {

    private final RegistrationService registrationService;

    public RegistrationPresenter(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public List<String> getStates() {
        return List.of("Maharashtra", "Gujarat", "Karnataka", "Tamil Nadu");
    }

    public Map<String, List<String>> getStateCityMap() {
        return Map.of(
                "Maharashtra", List.of("Mumbai", "Pune", "Nagpur"),
                "Gujarat", List.of("Ahmedabad", "Surat", "Vadodara"),
                "Karnataka", List.of("Bangalore", "Mysore"),
                "Tamil Nadu", List.of("Chennai", "Coimbatore")
        );
    }

    public void registerUser(User user) {
        user.setCreationDate(Instant.now());
        registrationService.save(user);
    }
}