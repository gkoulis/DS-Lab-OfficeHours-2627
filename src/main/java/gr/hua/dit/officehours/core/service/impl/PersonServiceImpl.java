package gr.hua.dit.officehours.core.service.impl;

import gr.hua.dit.officehours.core.service.RegistrationService;
import gr.hua.dit.officehours.core.service.model.RegistrationRequest;
import gr.hua.dit.officehours.core.service.model.RegistrationResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link gr.hua.dit.officehours.core.service.RegistrationService}.
 */
@Service
public class PersonServiceImpl implements RegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    public PersonServiceImpl() {
    }

    @Override
    public RegistrationResult register(final RegistrationRequest request) {
        throw new UnsupportedOperationException();
    }
}
