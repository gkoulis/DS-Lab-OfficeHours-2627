package gr.hua.dit.officehours.core.service.model;

/**
 * RegisterPersonResult DTO.
 */
public record RegisterPersonResult(
    boolean created,
    String reason,
    PersonView personView
) {

    public static RegisterPersonResult success(final PersonView personView) {
        if (personView == null) throw new NullPointerException();
        return new RegisterPersonResult(true, null, personView);
    }

    public static RegisterPersonResult fail(final String reason) {
        if (reason == null) throw new NullPointerException();
        if (reason.isBlank()) throw new IllegalArgumentException();
        return new RegisterPersonResult(false, reason, null);
    }
}
