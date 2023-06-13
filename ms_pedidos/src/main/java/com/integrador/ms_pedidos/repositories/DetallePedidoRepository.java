package com.integrador.ms_pedidos.repositories;

import com.integrador.ms_pedidos.models.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
}
