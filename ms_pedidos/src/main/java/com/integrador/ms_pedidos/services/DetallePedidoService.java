package com.integrador.ms_pedidos.services;

import com.integrador.ms_pedidos.models.DetallePedido;
import com.integrador.ms_pedidos.models.DetallePedidoResponse;
import com.integrador.ms_pedidos.repositories.DetallePedidoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DetallePedidoService {

    @Value("${ms.categry_product}")
    private String ms_category_product_path;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<DetallePedidoResponse> getAll(){
        return detallePedidoRepository.findAll().stream().map(x -> {
            return new DetallePedidoResponse(
                    x.getId(),
                    x.getPedidoId(),
                    x.getProductoId(),
                    getProductName(x.getProductoId()),
                    x.getCantidad()
            );
        }).collect(Collectors.toList());
    }

    public DetallePedido create(DetallePedido detallePedido){
        return detallePedidoRepository.save(detallePedido);
    }

    public DetallePedido Update(DetallePedido detallePedido, Long id){
        DetallePedido pedido = detallePedidoRepository.findById(id).get();
        pedido.setPedidoId(detallePedido.getPedidoId());
        pedido.setProductoId(detallePedido.getProductoId());
        pedido.setCantidad(detallePedido.getCantidad());
        return detallePedidoRepository.save(pedido);
    }

    private String getProductName(Long id) {
        RestTemplate restTemplate =new RestTemplate();
        String url = ms_category_product_path;
        log.warn("URL: "+url);
        ResponseEntity<String> response= restTemplate.getForEntity(url+id, String.class);
        return response.getBody();
    }
}
