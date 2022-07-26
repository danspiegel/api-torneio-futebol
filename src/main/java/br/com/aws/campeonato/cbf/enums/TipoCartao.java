package br.com.aws.campeonato.cbf.enums;

import lombok.*;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum TipoCartao {

    A(1, "Amarelo"),
    V(2, "Vermelho");

    private Integer codigo;
    private String descricao;


    public static TipoCartao toEnum(Integer codTipoCartao) {
        return Arrays.stream(TipoCartao.values())
                .filter(tipoCartao -> tipoCartao.getCodigo() == codTipoCartao)
                .findFirst()
                .orElse(null);
    }

}
