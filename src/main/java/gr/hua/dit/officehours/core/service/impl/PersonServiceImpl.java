package gr.hua.dit.officehours.core.service.impl;

import gr.hua.dit.officehours.core.model.Person;
import gr.hua.dit.officehours.core.model.PersonType;
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
    // TODO Δηλώστε τα Ports, δηλαλή τις κλάσεις οι οποίες αναλαμβάνουν την επικοινωνία με την εξωτερική υπηρεσία.

    public PersonServiceImpl(final PersonRepository personRepository,
                             final PersonMapper personMapper) {
        if (personRepository == null) throw new NullPointerException();
        if (personMapper == null) throw new NullPointerException();

        this.personRepository = personRepository;
        this.personMapper = personMapper;
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

        // TODO Χρησιμοποιήστε την εξωτερική υπηρεσία για να επαληθεύσετε τον αριθμό τηλεφώνου.

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

        // TODO Χρησιμοποιήστε την εξωτερική υπηρεσία για να βρείτε τον τύπο του ΑΜ (αν υπάρχει).
        // TODO Αφού το NOC μου επιστρέφει τον τύπο, μπορώ να το αφαιρέσω από τη φόρμα για να βελτιώσω την εμπειρία του χρήστη;

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

        // TODO Χρησιμοποιήστε την εξωτερική υπηρεσία για να στείλετε SMS στο νέο χρήστη!

        // Map `Person` to `PersonView`.
        // --------------------------------------------------

        final PersonView personView = this.personMapper.convertPersonToPersonView(person);

        // --------------------------------------------------

        return RegistrationResult.success(personView);
    }
}
