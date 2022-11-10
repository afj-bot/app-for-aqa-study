package com.afj.solution.buyitapp.payload.response;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.afj.solution.buyitapp.model.enums.OrderStatus;
import com.afj.solution.buyitapp.model.product.Product;

import static java.util.Objects.requireNonNull;

/**
 * @author Tomash Gombosh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "OrderResponse", description = "Order response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 621395641796331484L;

    @ApiModelProperty(
            name = "id",
            dataType = "String",
            value = "Order id"
    )
    private UUID id;

    @ApiModelProperty(
            name = "products",
            dataType = "Object",
            value = "Set of the products in the order"
    )
    private Set<Product> products;

    @ApiModelProperty(
            name = "total",
            dataType = "float",
            value = "Total price of all products"
    )
    private float total;

    @ApiModelProperty(
            name = "status",
            dataType = "OrderStatus",
            value = "Order status"
    )
    private OrderStatus status;

    @ApiModelProperty(
            name = "createdAt",
            dataType = "ZonedDateTime",
            value = "User account creation date"
    )
    private ZonedDateTime createdAt;

    @ApiModelProperty(
            name = "updatedAt",
            dataType = "ZonedDateTime",
            value = "User account update date"
    )
    private ZonedDateTime updatedAt;

    public OrderResponse(final Consumer<OrderResponse> builder) {
        requireNonNull(builder).accept(this);
    }
}
