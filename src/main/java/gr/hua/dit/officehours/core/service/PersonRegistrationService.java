package gr.hua.dit.officehours.core.service;

import gr.hua.dit.officehours.core.service.model.RegisterPersonRequest;
import gr.hua.dit.officehours.core.service.model.RegisterPersonResult;

/**
 * Service for managing person registration.
 */
public interface PersonRegistrationService {

    RegisterPersonResult register(final RegisterPersonRequest request);
}
