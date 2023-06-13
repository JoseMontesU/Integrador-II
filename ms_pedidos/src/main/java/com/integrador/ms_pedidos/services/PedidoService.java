package com.integrador.ms_pedidos.services;

import com.integrador.ms_pedidos.models.Pedido;
import com.integrador.ms_pedidos.models.PedidoResponse;
import com.integrador.ms_pedidos.repositories.PedidoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PedidoService {

    @Value("${ms.user}")
    private String ms_user_path;

    @Autowired
    PedidoRepository pedidoRepository;

    public List<PedidoResponse> getAll(){
        return pedidoRepository.findAll().stream().map(x ->{
            PedidoResponse pedidoResponse = new PedidoResponse();
            pedidoResponse.setId(x.getId());
            pedidoResponse.setUserId(x.getUserId());
            pedidoResponse.setUserName(getUsername(x.getUserId()));
            pedidoResponse.setFechaPedido(x.getFechaPedido());
            pedidoResponse.setFechaEntrega(x.getFechaEntrega());
            return pedidoResponse;
        }).collect(Collectors.toList());
    }

    public Pedido createPedido(Pedido pedido){
        return pedidoRepository.save(pedido);
    }

    private String getUsername(String id) {
        RestTemplate restTemplate =new RestTemplate();
        String url = ms_user_path;
        log.warn("URL: "+url);
        ResponseEntity<String> response= restTemplate.getForEntity(url+id, String.class);
        return response.getBody();
    }

}
