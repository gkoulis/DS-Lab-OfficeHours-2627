package gr.hua.dit.officehours.core.service.model;

/**
 * RegisterPersonResult DTO.
 */
public record PersonRegistrationResult(
    boolean created,
    String reason,
    PersonView personView
) {

    public static PersonRegistrationResult success(final PersonView personView) {
        if (personView == null) throw new NullPointerException();
        return new PersonRegistrationResult(true, null, personView);
    }

    public static PersonRegistrationResult fail(final String reason) {
        if (reason == null) throw new NullPointerException();
        if (reason.isBlank()) throw new IllegalArgumentException();
        return new PersonRegistrationResult(false, reason, null);
    }
}
