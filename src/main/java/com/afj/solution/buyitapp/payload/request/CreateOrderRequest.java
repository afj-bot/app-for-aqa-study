package com.afj.solution.buyitapp.payload.request;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tomash Gombosh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CreateProductRequest", description = "Create product request model")
public class CreateOrderRequest implements Serializable {

    private static final long serialVersionUID = 2968630540737468996L;

    @ApiModelProperty(
            name = "productIds",
            dataType = "Array",
            value = "Product ids",
            example = "['4b04d000-6738-4e6d-8d53-8dfb9e4f4761', '6901e91c-b229-4cc6-9889-0e0116077877']",
            required = true
    )
    @NotEmpty(message = "Can not create an order without products")
    private List<UUID> productIds;
}
