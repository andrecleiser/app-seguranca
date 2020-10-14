package pt.com.hc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class SegurancaDto {

    private String hashSenha;
    private String chavePrivadaEncripty;
}