package com.integrador.ms_pedidos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse {
    private Long Id;
    private String userId;
    private String userName;
    private LocalDate fechaPedido;
    private LocalDate fechaEntrega;
}
