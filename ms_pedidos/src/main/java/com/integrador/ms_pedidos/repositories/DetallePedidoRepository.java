package com.integrador.ms_pedidos.repositories;

import com.integrador.ms_pedidos.models.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> getDetallePedidoByPedidoId(Long id);

}
