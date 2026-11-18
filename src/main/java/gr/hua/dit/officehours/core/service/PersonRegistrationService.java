package gr.hua.dit.officehours.core.service;

import gr.hua.dit.officehours.core.service.model.PersonRegistrationRequest;
import gr.hua.dit.officehours.core.service.model.PersonRegistrationResult;

/**
 * Service for managing person registration.
 */
public interface PersonRegistrationService {

    PersonRegistrationResult register(final PersonRegistrationRequest request, final boolean notify);

    default PersonRegistrationResult register(final PersonRegistrationRequest request) {
        return this.register(request, true);
    }
}
