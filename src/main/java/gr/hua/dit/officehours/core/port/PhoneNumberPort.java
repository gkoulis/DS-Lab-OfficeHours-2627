package gr.hua.dit.officehours.core.port;

import gr.hua.dit.officehours.core.port.dto.PhoneNumberValidationResult;

/**
 * Port to external service for managing phone numbers.
 */
public interface PhoneNumberPort {

    PhoneNumberValidationResult validate(final String rawPhoneNumber);
}
