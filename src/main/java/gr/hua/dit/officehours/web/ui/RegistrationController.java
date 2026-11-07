package gr.hua.dit.officehours.web.ui;

import gr.hua.dit.officehours.core.model.PersonType;
import gr.hua.dit.officehours.core.service.RegistrationService;
import gr.hua.dit.officehours.core.service.model.RegistrationRequest;
import gr.hua.dit.officehours.core.service.model.RegistrationResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * UI controller for managing teacher/student registration.
 */
@Controller
public class RegistrationController {

    private final RegistrationService personRegistrationService;

    public RegistrationController(final RegistrationService personRegistrationService) {
        if (personRegistrationService == null) throw new NullPointerException();
        this.personRegistrationService = personRegistrationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(final Model model) {
        // TODO if user is authenticated, redirect to tickets
        // Initial data for the form.
        final RegistrationRequest registrationRequest = new RegistrationRequest(PersonType.STUDENT, "", "", "", "", "", "");
        model.addAttribute("registrationRequest", registrationRequest);
        return "register";
    }

    @PostMapping("/register")
    public String handleFormSubmission(
        @ModelAttribute("registrationRequest") final RegistrationRequest registrationRequest,
        final Model model
    ) {
        // TODO if user is authenticated, redirect to tickets
        // TODO Validate form (email format, size, blank, etc)
        // TODO if form has errors, show the form (with pre-filled data)
        // TODO Try to create Person.
        // TODO Handle Person creation failure.
        // TODO Redirect to login.
        final RegistrationResult registrationResult = this.personRegistrationService.register(registrationRequest);
        if (registrationResult.created()) {
            return "redirect:/login"; // registration successful - redirect to login form (not yet ready)
        }
        model.addAttribute("registrationRequest", registrationRequest); // Pass the same form data.
        model.addAttribute("errorMessage", registrationResult.reason()); // Show an error message!
        return "register";
    }
}
