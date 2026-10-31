package gr.hua.dit.officehours.web.rest;

import gr.hua.dit.officehours.core.model.Person;
import gr.hua.dit.officehours.core.model.PersonType;
import gr.hua.dit.officehours.core.repository.PersonRepository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticsResource {

    private final PersonRepository personRepository;

    public DiagnosticsResource(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping(value = "/diagnostics", produces = MediaType.TEXT_MARKDOWN_VALUE)
    public String test() {
        final StringBuilder sb = new StringBuilder();

        sb.append("# Diagnostics");
        sb.append("\n\n");

        final var students = this.personRepository.findAllByType(PersonType.STUDENT);
        final String studentsString = String.join(
            "\n", students.stream().map(Person::toString).toList());
        sb.append("## Students").append("\n").append(studentsString);

        sb.append("\n\n");

        final var teachers = this.personRepository.findAllByType(PersonType.TEACHER);
        final String teachersString = String.join(
            "\n", teachers.stream().map(Person::toString).toList());
        sb.append("## Teachers").append("\n").append(teachersString);

        return sb.toString();
    }
}
