package org.bibliotecaviva.backend.api.controllers;


import lombok.RequiredArgsConstructor;
import org.bibliotecaviva.backend.aplication.dtos.request.WorkRequestDTO;
import org.bibliotecaviva.backend.aplication.dtos.response.WorkResponseDTO;
import org.bibliotecaviva.backend.aplication.services.WorkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkController {

    private final WorkService service;

    @GetMapping
    public WorkResponseDTO getALL(@RequestBody WorkRequestDTO request) {
        return service.getAll(request);
    }
}
