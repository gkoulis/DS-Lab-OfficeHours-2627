package gr.hua.dit.officehours.web.ui;

import gr.hua.dit.officehours.core.model.PersonType;
import gr.hua.dit.officehours.core.service.PersonRegistrationService;
import gr.hua.dit.officehours.core.service.model.RegisterPersonRequest;
import gr.hua.dit.officehours.core.service.model.RegisterPersonResult;

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

    private final PersonRegistrationService personRegistrationService;

    public RegistrationController(final PersonRegistrationService personRegistrationService) {
        if (personRegistrationService == null) throw new NullPointerException();
        this.personRegistrationService = personRegistrationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(final Model model) {
        // TODO if user is authenticated, redirect to tickets
        // Initial data for the form.
        final RegisterPersonRequest registerPersonRequest = new RegisterPersonRequest(PersonType.STUDENT, "", "", "", "", "", "");
        model.addAttribute("registerPersonRequest", registerPersonRequest);
        return "register";
    }

    @PostMapping("/register")
    public String handleFormSubmission(
        @ModelAttribute("registerPersonRequest") final RegisterPersonRequest registerPersonRequest,
        final Model model
    ) {
        // TODO if user is authenticated, redirect to tickets
        // TODO Validate form (email format, size, blank, etc)
        // TODO if form has errors, show the form (with pre-filled data)
        // TODO Try to create Person.
        // TODO Handle Person creation failure.
        // TODO Redirect to login.
        final RegisterPersonResult registerPersonResult = this.personRegistrationService.register(registerPersonRequest);
        if (registerPersonResult.created()) {
            return "redirect:/login"; // registration successful - redirect to login form (not yet ready)
        }
        model.addAttribute("registerPersonRequest", registerPersonRequest); // Pass the same form data.
        model.addAttribute("errorMessage", registerPersonResult.reason()); // Show an error message!
        return "register";
    }
}
