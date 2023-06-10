package com.integrador.ms_pedidos.services;

import com.integrador.ms_pedidos.models.Pedido;
import com.integrador.ms_pedidos.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    public List<Pedido> getAll(){
        return pedidoRepository.findAll();
    }

    public Pedido createPedido(Pedido pedido){
        String userId = pedido.getUserName();
        String productId = pedido.getProductName();
        pedido.setUserName(getUsername(userId));
        Long value = Long.valueOf(productId);
        pedido.setProductName(getProductName(value));
        return pedidoRepository.save(pedido);
    }

    private String getProductName(Long id) {
        RestTemplate restTemplate =new RestTemplate();
        String url = "http://ms-category-product/api/v1/products/name/";
        ResponseEntity<String> response= restTemplate.getForEntity(url+id, String.class);
        return response.getBody();
    }

    private String getUsername(String id) {
        RestTemplate restTemplate =new RestTemplate();
        String url = "http://ms-users/api/user/name/";
        ResponseEntity<String> response= restTemplate.getForEntity(url+id, String.class);
        return response.getBody();
    }

}
