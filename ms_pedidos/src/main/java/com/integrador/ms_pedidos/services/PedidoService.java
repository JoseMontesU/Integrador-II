package com.integrador.ms_pedidos.services;

import com.integrador.ms_pedidos.models.Pedido;
import com.integrador.ms_pedidos.repositories.PedidoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
public class PedidoService {

    @Value("${ms.user}")
    private String ms_user_path;

    @Value("${ms.categry_product}")
    private String ms_category_product_path;

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
        String url = ms_category_product_path;
        log.warn("URL: "+url);
        ResponseEntity<String> response= restTemplate.getForEntity(url+id, String.class);
        return response.getBody();
    }

    private String getUsername(String id) {
        RestTemplate restTemplate =new RestTemplate();
        String url = ms_user_path;
        log.warn("URL: "+url);
        ResponseEntity<String> response= restTemplate.getForEntity(url+id, String.class);
        return response.getBody();
    }

}
