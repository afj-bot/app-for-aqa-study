package com.afj.solution.buyitapp.payload.response;

import java.io.Serializable;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Objects.requireNonNull;

/**
 * @author Tomash Gombosh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ProductResponse", description = "Product response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse implements Serializable {

    private static final long serialVersionUID = -7667161353186665283L;

    @ApiModelProperty(
            name = "id",
            dataType = "String",
            value = "Product Id",
            example = "90656410-8926-483e-ace2-57f0e2131515"
    )
    private String id;

    @ApiModelProperty(
            name = "name",
            dataType = "String",
            value = "Product name",
            example = "Flower"
    )
    private String name;

    @ApiModelProperty(
            name = "description",
            dataType = "String",
            value = "Product description",
            example = "Beautiful red flower"
    )
    private String description;

    @ApiModelProperty(
            name = "price",
            dataType = "float",
            value = "Product price",
            example = "44.4 USD"
    )
    private String price;
    @ApiModelProperty(
            name = "quantity",
            dataType = "int",
            value = "Quantity of the product",
            example = "5"
    )
    private int quantity;
    @ApiModelProperty(
            name = "image",
            dataType = "Object",
            value = "Product image",
            example = "{ 'name': 'flower.jpg', 'picture': 'base64image' }"
    )
    private ImageResponse image;

    public ProductResponse(final Consumer<ProductResponse> builder) {
        requireNonNull(builder).accept(this);
    }
}

