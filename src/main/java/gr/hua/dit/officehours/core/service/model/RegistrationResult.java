package gr.hua.dit.officehours.core.service.model;

/**
 * RegisterPersonResult DTO.
 */
public record RegistrationResult(
    boolean created,
    String reason,
    PersonView personView
) {

    public static RegistrationResult success(final PersonView personView) {
        if (personView == null) throw new NullPointerException();
        return new RegistrationResult(true, null, personView);
    }

    public static RegistrationResult fail(final String reason) {
        if (reason == null) throw new NullPointerException();
        if (reason.isBlank()) throw new IllegalArgumentException();
        return new RegistrationResult(false, reason, null);
    }
}
