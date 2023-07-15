package com.integrador.ms_pedidos.controllers;

import com.integrador.ms_pedidos.models.DetallePedido;
import com.integrador.ms_pedidos.repositories.DetallePedidoRepository;
import com.integrador.ms_pedidos.services.DetallePedidoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetallePedidoControllerTest {

    @Mock
    private DetallePedidoService detallePedidoService;

    @InjectMocks
    private DetallePedidoController detallePedidoController;

    @Mock
    private DetallePedidoRepository detallePedidoRepository;


    @Test
    @DisplayName("Should return an error when the given id does not exist")
    void deleteDetallePedidoWhenIdDoesNotExistThenReturnError() {
        Long id = 1L;
        when(detallePedidoService.deleteDetallePedido(id)).thenReturn(null);

        ResponseEntity<?> response = detallePedidoController.delete(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(detallePedidoService, times(1)).deleteDetallePedido(id);
    }

    @Test
    @DisplayName("Should delete the DetallePedido when the given id exists")
    void deleteDetallePedidoWhenIdExists() {
        Long id = 1L;
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setId(id);
        ResponseEntity<?> response = detallePedidoController.delete(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(detallePedidoService, times(1)).deleteDetallePedido(id);
    }

    @Test
    @DisplayName("Should return a 400 status when the DetallePedido object is null")
    void updateWhenDetallePedidoIsNullThenReturn400() {
        Long id = 1L;
        DetallePedido detallePedido = null;

        assertThrows(IllegalArgumentException.class, () -> {
            detallePedidoController.update(detallePedido, id);
        });

        verify(detallePedidoService, never()).Update(detallePedido, id);
    }

    @Test
    @DisplayName("Should return a 404 status when the given id does not exist")
    void updateWhenIdDoesNotExistThenReturn404() {
        Long id = 1L;
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setId(id);

        when(detallePedidoService.Update(detallePedido, id)).thenReturn(null);

        ResponseEntity<DetallePedido> response = detallePedidoController.update(detallePedido, id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(detallePedidoService, times(1)).Update(detallePedido, id);
    }

    @Test
    @DisplayName("Should update the DetallePedido when the given id exists")
    void updateWhenIdExists() {
        Long id = 1L;
        DetallePedido detallePedido = new DetallePedido(id, 1L, 1L, 5);
        DetallePedido updatedDetallePedido = new DetallePedido(id, 1L, 1L, 10);
        when(detallePedidoService.Update(detallePedido, id)).thenReturn(updatedDetallePedido);
        ResponseEntity<DetallePedido> response = detallePedidoController.update(detallePedido, id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDetallePedido, response.getBody());
        verify(detallePedidoService, times(1)).Update(detallePedido, id);
    }

    @Test
    @DisplayName("Should throw an exception when the request body is null")
    void createDetallePedidoWhenRequestBodyIsNullThenThrowException() {
        DetallePedido detallePedido = null;

        assertThrows(IllegalArgumentException.class, () -> {
            detallePedidoController.create(detallePedido);
        });

        verify(detallePedidoService, never()).create(detallePedido);
    }

    @Test
    @DisplayName("Should return a CREATED status when the DetallePedido is successfully created")
    void createDetallePedidoReturnsCreatedStatus() {
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setId(1L);
        detallePedido.setPedidoId(1L);
        detallePedido.setProductoId(1L);
        detallePedido.setCantidad(5);
        when(detallePedidoService.create(detallePedido)).thenReturn(detallePedido);
        ResponseEntity<DetallePedido> response = detallePedidoController.create(detallePedido);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(detallePedido, response.getBody());
        verify(detallePedidoService, times(1)).create(detallePedido);
    }

    @Test
    @DisplayName("Should create and return the DetallePedido when the request is valid")
    void createDetallePedidoWhenRequestIsValid() {
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setId(1L);
        detallePedido.setPedidoId(1L);
        detallePedido.setProductoId(1L);
        detallePedido.setCantidad(5);

        when(detallePedidoService.create(detallePedido)).thenReturn(detallePedido);

        ResponseEntity<DetallePedido> response = detallePedidoController.create(detallePedido);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(detallePedido, response.getBody());

        verify(detallePedidoService, times(1)).create(detallePedido);
    }
}