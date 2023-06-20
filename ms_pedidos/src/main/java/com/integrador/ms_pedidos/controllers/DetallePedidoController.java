package com.integrador.ms_pedidos.controllers;

import com.integrador.ms_pedidos.models.DetallePedido;
import com.integrador.ms_pedidos.models.DetallePedidoResponse;
import com.integrador.ms_pedidos.services.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedido/detallepedido")
@CrossOrigin("*")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedidoResponse>> getAll(){
        return new ResponseEntity<>(detallePedidoService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<DetallePedidoResponse>> getByPedidoId(@PathVariable Long id){
        return new ResponseEntity<>(detallePedidoService.getByPedidoId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DetallePedido> create(@RequestBody DetallePedido detallePedido){
        return new ResponseEntity<>(detallePedidoService.create(detallePedido),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> update(@RequestBody DetallePedido detallePedido,@PathVariable Long id){
        return new ResponseEntity<>(detallePedidoService.Update(detallePedido,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return new ResponseEntity<>(detallePedidoService.deleteDetallePedido(id),HttpStatus.OK);
    }

}
