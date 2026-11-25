package gr.hua.dit.officehours.web.ui;

import gr.hua.dit.officehours.core.model.PersonType;
import gr.hua.dit.officehours.core.service.PersonRegistrationService;
import gr.hua.dit.officehours.core.service.model.PersonRegistrationRequest;
import gr.hua.dit.officehours.core.service.model.PersonRegistrationResult;
import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * UI controller for managing teacher/student registration.
 */
@Controller
public class PersonRegistrationController {

    private final PersonRegistrationService personRegistrationService;

    public PersonRegistrationController(final PersonRegistrationService personRegistrationService) {
        if (personRegistrationService == null) throw new NullPointerException();
        this.personRegistrationService = personRegistrationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(
        final Authentication authentication,
        final Model model
    ) {
        if (AuthUtils.isAuthenticated(authentication)) {
            return "redirect:/profile";
        }
        // Initial data for the form.
        final PersonRegistrationRequest personRegistrationRequest = new PersonRegistrationRequest(PersonType.STUDENT, "", "", "", "", "", "");
        model.addAttribute("personRegistrationRequest", personRegistrationRequest);
        return "register";
    }

    @PostMapping("/register")
    public String handleFormSubmission(
        final Authentication authentication,
        @Valid @ModelAttribute("personRegistrationRequest") final PersonRegistrationRequest personRegistrationRequest,
        final BindingResult bindingResult, // IMPORTANT: BindingResult **MUST** come immediately after the @Valid argument!
        final Model model
    ) {
        if (AuthUtils.isAuthenticated(authentication)) {
            return "redirect:/profile"; // already logged in.
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        final PersonRegistrationResult personRegistrationResult = this.personRegistrationService.register(personRegistrationRequest);
        if (personRegistrationResult.created()) {
            return "redirect:/login"; // registration successful - redirect to login form (not yet ready)
        }
        model.addAttribute("personRegistrationResult", personRegistrationResult); // Pass the same form data.
        model.addAttribute("errorMessage", personRegistrationResult.reason()); // Show an error message!
        return "register";
    }
}
