package gr.hua.dit.officehours.core.service.model;

import gr.hua.dit.officehours.core.model.PersonType;

/**
 * DTO for requesting the registration of a Person.
 */
public record RegistrationRequest(
    PersonType type,
    String huaId,
    String firstName,
    String lastName,
    String emailAddress,
    String mobilePhoneNumber,
    String rawPassword
) {}
