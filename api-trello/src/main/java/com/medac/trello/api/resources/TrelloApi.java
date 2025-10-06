package com.medac.trello.api.resources;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public interface TrelloApi {

    String BASE_API_PATH = "/trello/v1/";
}
