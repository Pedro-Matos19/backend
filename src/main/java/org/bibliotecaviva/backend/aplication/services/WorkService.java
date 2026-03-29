package org.bibliotecaviva.backend.aplication.services;

import lombok.RequiredArgsConstructor;
import org.bibliotecaviva.backend.aplication.dtos.response.WorkResponseDTO;
import org.bibliotecaviva.backend.aplication.mappers.WorkMapper;
import org.bibliotecaviva.backend.infrastructure.repositories.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class WorkService {

    private final WorkRepository workRepository;
    private final WorkMapper workMapper;

    public List<WorkResponseDTO> getAll(){

        // usando uma iterface para sumario pra nao puxar
        // todos os dados de todas as obras toda vez
        return workRepository.findAllSummary()
                .stream()
                .map(workMapper::toWorkDTO)
                .toList();
    }

}
