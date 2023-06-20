package com.integrador.ms_pedidos.controllers;

import com.integrador.ms_pedidos.models.Pedido;
import com.integrador.ms_pedidos.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin("*")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(pedidoService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getByUserId(@PathVariable String id){
        return new ResponseEntity<>(pedidoService.getByUserId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Pedido pedido){
        return new ResponseEntity<>(pedidoService.createPedido(pedido),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Pedido pedido, @PathVariable Long id){
        return new ResponseEntity<>(pedidoService.updatePedido(pedido,id),HttpStatus.OK);
    }
}
