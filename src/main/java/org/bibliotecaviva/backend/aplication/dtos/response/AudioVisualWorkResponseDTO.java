package org.bibliotecaviva.backend.aplication.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AudioVisualWorkResponseDTO extends WorkResponseDTO{
    private String url;
}
