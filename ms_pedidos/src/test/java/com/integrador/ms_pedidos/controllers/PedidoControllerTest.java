package com.integrador.ms_pedidos.controllers;

import com.integrador.ms_pedidos.models.Pedido;
import com.integrador.ms_pedidos.services.PedidoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    PedidoService pedidoService;

    @InjectMocks
    PedidoController pedidoController;

    //    @Test
//    @DisplayName("Should throw an exception when the pedido already exists")
//    void createPedidoWhenPedidoAlreadyExistsThenThrowException() {
//        Pedido pedido = new Pedido();
//        pedido.setId(1L);
//        pedido.setUserId("user123");
//        pedido.setFechaPedido(LocalDate.now());
//        pedido.setFechaEntrega(LocalDate.now().plusDays(7));
//
//        when(pedidoService.createPedido(pedido)).thenThrow(DataIntegrityViolationException.class);
//
//        assertThrows(DataIntegrityViolationException.class, () -> pedidoController.create(pedido));
//
//        verify(pedidoService, times(1)).createPedido(pedido);
//    }


//    @Test
//    @DisplayName("Should throw an exception when the given id does not exist")
//    void updatePedidoWhenIdDoesNotExistThenThrowException() {
//        Long id = 1L;
//        Pedido pedido = new Pedido();
//        pedido.setId(id);
//        pedido.setUserId("user123");
//        pedido.setFechaPedido(LocalDate.now());
//        pedido.setFechaEntrega(LocalDate.now().plusDays(7));
//
//        when(pedidoService.updatePedido(pedido, id)).thenThrow(DataIntegrityViolationException.class);
//
//        ResponseEntity<?> response = pedidoController.update(pedido, id);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        verify(pedidoService, times(1)).updatePedido(pedido, id);
//    }

    @Test
    @DisplayName("Should update the pedido when the given id exists")
    void updatePedidoWhenIdExists() {        // Create a sample pedido object
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setUserId("user123");
        pedido.setFechaPedido(LocalDate.now());
        pedido.setFechaEntrega(LocalDate.now().plusDays(7));
        when(pedidoService.updatePedido(pedido, pedido.getId())).thenReturn(pedido);
        ResponseEntity<?> response = pedidoController.update(pedido, pedido.getId());
        verify(pedidoService, times(1)).updatePedido(pedido, pedido.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedido, response.getBody());
    }

    @Test
    @DisplayName("Should create a new pedido and return status CREATED")
    void createNewPedido() {
        Pedido pedido = new Pedido();
        pedido.setUserId("user123");
        pedido.setFechaPedido(LocalDate.now());
        pedido.setFechaEntrega(LocalDate.now().plusDays(7));
        when(pedidoService.createPedido(pedido)).thenReturn(pedido);
        ResponseEntity<?> response = pedidoController.create(pedido);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pedido, response.getBody());
        verify(pedidoService, times(1)).createPedido(pedido);
    }
}