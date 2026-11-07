package gr.hua.dit.officehours.core.service;

import gr.hua.dit.officehours.core.service.model.RegistrationRequest;
import gr.hua.dit.officehours.core.service.model.RegistrationResult;

/**
 * Service for managing person registration.
 */
public interface RegistrationService {

    RegistrationResult register(final RegistrationRequest request);
}
