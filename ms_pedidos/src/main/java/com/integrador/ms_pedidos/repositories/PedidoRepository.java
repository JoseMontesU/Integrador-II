package com.integrador.ms_pedidos.repositories;

import com.integrador.ms_pedidos.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}
