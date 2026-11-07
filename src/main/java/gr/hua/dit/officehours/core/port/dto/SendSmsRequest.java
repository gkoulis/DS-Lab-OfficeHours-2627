package gr.hua.dit.officehours.core.port.dto;

/**
 * SendSmsRequest DTO.
 */
public record SendSmsRequest(String e164, String content) {}
