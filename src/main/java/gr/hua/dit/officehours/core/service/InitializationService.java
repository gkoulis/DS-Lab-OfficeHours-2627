package gr.hua.dit.officehours.core.service;

import gr.hua.dit.officehours.core.model.PersonType;
import gr.hua.dit.officehours.core.service.model.PersonRegistrationRequest;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Initializes application.
 *
 * @deprecated see {@link gr.hua.dit.officehours.core.fixture.PersonFixture}
 */
// @Service
@Deprecated
public class InitializationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializationService.class);

    private final PersonRegistrationService personRegistrationService;
    private final AtomicBoolean initialized;

    public InitializationService(final PersonRegistrationService personRegistrationService) {
        if (personRegistrationService == null) throw new NullPointerException();
        this.personRegistrationService = personRegistrationService;
        this.initialized = new AtomicBoolean(false);
    }

    // @PostConstruct
    public void populateDatabaseWithInitialData() {
        final boolean alreadyInitialized = this.initialized.getAndSet(true);
        if (alreadyInitialized) {
            LOGGER.warn("Database initialization skipped: initial data has already been populated.");
            return;
        }
        LOGGER.info("Starting database initialization with initial data...");
        final List<PersonRegistrationRequest> personRegistrationRequestList = List.of(
            new PersonRegistrationRequest(
                PersonType.TEACHER,
                "t0001",
                "Dimitris",
                "Gkoulis",
                "gkoulis@hua.gr",
                "+306900000000",
                "1234"
            ),
            new PersonRegistrationRequest(
                PersonType.STUDENT,
                "it2023001",
                "Test 1",
                "Test 1",
                "it2023001@hua.gr",
                "+306900000001",
                "1234"
            ),
            new PersonRegistrationRequest(
                PersonType.STUDENT,
                "it2023002",
                "Test 2",
                "Test 2",
                "it2023002@hua.gr",
                "+306900000002",
                "1234"
            )
        );
        for (final var personRegistrationRequest : personRegistrationRequestList) {
            this.personRegistrationService.register(personRegistrationRequest, false); // do not send SMS
        }
        LOGGER.info("Database initialization completed successfully.");
    }
}
