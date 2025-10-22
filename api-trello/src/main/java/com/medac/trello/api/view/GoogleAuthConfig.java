package com.medac.trello.api.view;

import java.util.Set;

public record GoogleAuthConfig(String redirectUri,
                               String clientId,
                               Set<String> scope) {
}
