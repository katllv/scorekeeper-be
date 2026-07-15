package com.katllv.scorekeeper_be.user.dto;

import java.util.UUID;

public record UserResponse(UUID id, String username, String displayName) {
}
