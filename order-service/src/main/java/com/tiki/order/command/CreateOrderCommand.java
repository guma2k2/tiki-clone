package com.tiki.order.command;

import com.tiki.order.dto.OrderDetailDto;
import com.tiki.order.entity.Order;
import com.tiki.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private Long orderId;
    private OrderStatus status;
    private String reasonFailed;
    private List<OrderDetailDto> details;


    public CreateOrderCommand(Order order, List<OrderDetailDto> orderDetailDtos) {
        this.orderId = order.getId();
        this.status = order.getStatus();
        this.reasonFailed = order.getReasonFailed();
        this.details = orderDetailDtos;
    }
}
