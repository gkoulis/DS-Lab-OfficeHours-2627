package gr.hua.dit.officehours.core.service.impl;

import gr.hua.dit.officehours.core.model.Person;
import gr.hua.dit.officehours.core.model.PersonType;
import gr.hua.dit.officehours.core.port.LookupPort;
import gr.hua.dit.officehours.core.port.PhoneNumberPort;
import gr.hua.dit.officehours.core.port.SmsNotificationPort;
import gr.hua.dit.officehours.core.port.dto.PhoneNumberValidationResult;
import gr.hua.dit.officehours.core.repository.PersonRepository;
import gr.hua.dit.officehours.core.service.RegistrationService;
import gr.hua.dit.officehours.core.service.mapper.PersonMapper;
import gr.hua.dit.officehours.core.service.model.RegistrationRequest;
import gr.hua.dit.officehours.core.service.model.RegistrationResult;
import gr.hua.dit.officehours.core.service.model.PersonView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link gr.hua.dit.officehours.core.service.RegistrationService}.
 */
@Service
public class PersonServiceImpl implements RegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PhoneNumberPort phoneNumberPort;
    private final LookupPort lookupPort;
    private final SmsNotificationPort smsNotificationPort;

    public PersonServiceImpl(final PersonRepository personRepository,
                             final PersonMapper personMapper,
                             final PhoneNumberPort phoneNumberPort,
                             final LookupPort lookupPort,
                             final SmsNotificationPort smsNotificationPort) {
        if (personRepository == null) throw new NullPointerException();
        if (personMapper == null) throw new NullPointerException();
        if (phoneNumberPort == null) throw new NullPointerException();
        if (lookupPort == null) throw new NullPointerException();
        if (smsNotificationPort == null) throw new NullPointerException();

        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.phoneNumberPort = phoneNumberPort;
        this.lookupPort = lookupPort;
        this.smsNotificationPort = smsNotificationPort;
    }

    @Override
    public RegistrationResult register(final RegistrationRequest request) {
        if (request == null) throw new NullPointerException();

        // Unpack (we assume valid `CreatePersonRequest` instance)
        // --------------------------------------------------

        final PersonType type = request.type();
        final String huaId = request.huaId().strip(); // remove whitespaces
        final String firstName = request.firstName().strip();
        final String lastName = request.lastName().strip();
        final String emailAddress = request.emailAddress().strip();
        String mobilePhoneNumber = request.mobilePhoneNumber().strip();
        final String rawPassword = request.rawPassword();

        // Basic email address validation.
        // --------------------------------------------------

        if (!emailAddress.endsWith("@hua.gr")) {
            return RegistrationResult.fail("Only academic email addresses (@hua.gr) are allowed");
        }

        // Advanced mobile phone number validation.
        // --------------------------------------------------

        final PhoneNumberValidationResult phoneNumberValidationResult
            = this.phoneNumberPort.validate(mobilePhoneNumber);
        // TODO Exception handler - NOC is not available; try again later!
        if (!phoneNumberValidationResult.isValidMobile()) {
            return RegistrationResult.fail("Mobile Phone Number is not valid");
        }
        mobilePhoneNumber = phoneNumberValidationResult.e164();

        // --------------------------------------------------

        if (this.personRepository.existsByHuaIdIgnoreCase(huaId)) {
            return RegistrationResult.fail("HUA ID already registered");
        }

        if (this.personRepository.existsByEmailAddressIgnoreCase(emailAddress)) {
            return RegistrationResult.fail("Email Address already registered");
        }

        if (this.personRepository.existsByMobilePhoneNumber(mobilePhoneNumber)) {
            return RegistrationResult.fail("Mobile Phone Number already registered");
        }

        // --------------------------------------------------

        final PersonType personType_lookup = this.lookupPort.lookup(huaId).orElse(null);
        // TODO Exception handler - NOC is not available; try again later!
        if (personType_lookup == null) {
            return RegistrationResult.fail("Invalid HUA ID");
        }
        if (personType_lookup != type) {
            return RegistrationResult.fail("The provided person type does not match the actual one");
        }

        // --------------------------------------------------

        final String hashedPassword = rawPassword; // TODO Implement: encode password.

        // Instantiate person.
        // --------------------------------------------------

        Person person = new Person();
        person.setId(null); // auto generated
        person.setHuaId(huaId);
        person.setType(type);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmailAddress(emailAddress);
        person.setMobilePhoneNumber(mobilePhoneNumber);
        person.setPasswordHash(hashedPassword);
        person.setCreatedAt(null); // auto generated.

        // Persist person (save/insert to database)
        // --------------------------------------------------

        person = this.personRepository.save(person);

        // --------------------------------------------------

        final String content = String.format(
            "You have successfully registered for the Office Hours application. " +
                "Use your email (%s) to log in.",
            emailAddress);
        final boolean sent = this.smsNotificationPort.sendSms(mobilePhoneNumber, content);
        // TODO Exception handler - NOC is not available; try again later!
        // TODO Ας δοκιμάσουμε εγγραφή με σφάλμα στο SMS. Τι παρατηρούμε; Πώς το λύνουμε;
        if (!sent) {
            LOGGER.warn("SMS send to {} failed!", mobilePhoneNumber);
        }

        // Map `Person` to `PersonView`.
        // --------------------------------------------------

        final PersonView personView = this.personMapper.convertPersonToPersonView(person);

        // --------------------------------------------------

        return RegistrationResult.success(personView);
    }
}
