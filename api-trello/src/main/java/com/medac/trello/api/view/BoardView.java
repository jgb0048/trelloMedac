package com.medac.trello.api.view;

import java.time.LocalDateTime;

public record BoardView(
        Long id,
        String name,
        String description,
        LocalDateTime createdOn
) {
}
