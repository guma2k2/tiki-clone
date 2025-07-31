package com.tiki.search.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiki.search.dto.response.ProductVariantResponse;
import com.tiki.search.repository.ProductRepository;
import com.tiki.search.repository.client.ProductFeignClient;
import com.tiki.search.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaConsumer {

    ObjectMapper objectMapper = new ObjectMapper();


    ProductService productService;


    ProductFeignClient productFeignClient;


//    @KafkaListener(topics = "dbproduct.tiki-product.product_variants", groupId = "search-group")
//    public void listen(String messageJson) throws JsonProcessingException {
//        JsonNode root = objectMapper.readTree(messageJson);
//        JsonNode payload = root.path("payload");
//        String op = payload.path("op").asText();  // "c", "u", "d"
//        JsonNode after = payload.path("after");
//        JsonNode before = payload.path("before");
//        switch (op) {
//            case "c", "u":
//                Long variantId = after.path("id").asLong();
//                ProductVariantResponse productVariantResponse = productFeignClient.getProductVariantById(variantId).getResult();
//                productService.save(productVariantResponse);
//                break;
//            case "d":
//                if (Objects.nonNull(before)) {
//                    Long id= before.path("id").asLong();
//                    productService.delete(id);
//                }
//                break;
//            default:
//                log.warn("Unknown operation: {}", op);
//                break;
//        }
//    }

    @KafkaListener(topics = "mytopic", groupId = "search-group")
    public void listen(String message) {
        System.out.println("🔔 Received message: " + message);
    }
}
