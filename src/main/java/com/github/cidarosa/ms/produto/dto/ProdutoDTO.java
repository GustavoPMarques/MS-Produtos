package com.github.cidarosa.ms.produto.dto;

import com.github.cidarosa.ms.produto.entities.Produto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class ProdutoDTO {


    private Long id;


    @NotBlank(message = "Campo nome é requerido")
    @Size(min = 10, max = 100, message = "O nome deve ter entre 10 a 100 caracteres")
    private String nome;


    @NotBlank(message = "Campo descrição é requerido")
    @Size(min = 10, message = "Descrição deve ter no mínimo 100 caracteres")
    private String descricao;


    @NotNull(message = "O campo valor é requerido")
    @Positive(message = "O campo valor deve possuir um número positivo maior que 0")
    private Double valor;

    public ProdutoDTO(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        descricao = produto.getDescricao();
        valor = produto.getValor();
    }
}







