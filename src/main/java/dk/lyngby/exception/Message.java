package dk.lyngby.exception;

import java.time.LocalDateTime;

public record Message(int status, String message, String timestamp) {
}
