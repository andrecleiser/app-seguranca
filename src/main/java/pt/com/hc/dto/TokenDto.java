package pt.com.hc.dto;

import lombok.Getter;

public class TokenDto {

    @Getter
    private String tokenAcesso;

    public TokenDto(String tokenAcesso) {
        this.tokenAcesso = tokenAcesso;
    }
}
