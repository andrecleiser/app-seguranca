package pt.com.hc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ResponseErroDto {

    private String mensagem;
    private String stack;
}
