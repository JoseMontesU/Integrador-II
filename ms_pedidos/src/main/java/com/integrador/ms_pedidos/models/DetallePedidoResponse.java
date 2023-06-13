package com.integrador.ms_pedidos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoResponse {
    private Long id;
    private Long pedidoId;
    private Long productoId;
    private String productoName;
    private Integer cantidad;
}
