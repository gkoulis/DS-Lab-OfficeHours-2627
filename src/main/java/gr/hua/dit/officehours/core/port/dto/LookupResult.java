package gr.hua.dit.officehours.core.port.dto;

import gr.hua.dit.officehours.core.model.PersonType;

/**
 * LookupResult DTO.
 */
public record LookupResult(
    String raw,
    boolean exists,
    String huaId,
    PersonType type
) {}
