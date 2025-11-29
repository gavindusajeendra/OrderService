package com.gusto.microservices.dto;

import java.math.BigDecimal;

public record OrderRequest (Long id, String orderNumber, String skuCode, BigDecimal price, Integer quantity){

}
