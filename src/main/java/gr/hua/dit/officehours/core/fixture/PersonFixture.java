package gr.hua.dit.officehours.core.fixture;

import com.github.javafaker.Faker;

import gr.hua.dit.officehours.core.model.Person;
import gr.hua.dit.officehours.core.model.PersonType;
import gr.hua.dit.officehours.core.repository.PersonRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Random;

@Component
public final class PersonFixture {

    private static Logger LOGGER = LoggerFactory.getLogger(PersonFixture.class);

    private final PersonRepository personRepository;

    public PersonFixture(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostConstruct
    public void run() {
        final long peopleCount = this.personRepository.count();
        if (peopleCount > 0) {
            return;
        }

        final Faker fakeValuesGenerator = new Faker(new Locale("en-US"), new Random(42));

        final int studentsToCreate = 150;
        final int teachersToCreate = 10;

        // --------------------------------------------------

        for (int i=0; i<studentsToCreate; i++) {
            final String huaId = "it2024" + i;
            Person person = new Person();
            person.setId(null);
            person.setHuaId(huaId);
            person.setFirstName(fakeValuesGenerator.name().firstName());
            person.setLastName(fakeValuesGenerator.name().lastName());
            person.setEmailAddress(huaId + "@hua.gr");
            person.setMobilePhoneNumber(fakeValuesGenerator.phoneNumber().cellPhone());
            person.setType(PersonType.STUDENT);
            person.setPasswordHash("<hash>");
            this.personRepository.save(person);
        }

        // --------------------------------------------------

        for (int i=0; i<teachersToCreate; i++) {
            final String huaId = "staff" + i;
            Person person = new Person();
            person.setId(null);
            person.setHuaId(huaId);
            person.setFirstName(fakeValuesGenerator.name().firstName());
            person.setLastName(fakeValuesGenerator.name().lastName());
            person.setEmailAddress(huaId + "@hua.gr");
            person.setMobilePhoneNumber(fakeValuesGenerator.phoneNumber().cellPhone());
            person.setType(PersonType.TEACHER);
            person.setPasswordHash("<hash>");
            this.personRepository.save(person);
        }

        // --------------------------------------------------

        LOGGER.info("{} students created", studentsToCreate);
        LOGGER.info("{} teachers created", teachersToCreate);
    }
}
