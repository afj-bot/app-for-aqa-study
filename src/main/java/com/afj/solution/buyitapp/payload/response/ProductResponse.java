package com.afj.solution.buyitapp.payload.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Tomash Gombosh
 */
@ApiModel(value = "ProductResponse", description = "Product response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductResponse(

        @ApiModelProperty(
                name = "id",
                dataType = "String",
                value = "Product Id",
                example = "90656410-8926-483e-ace2-57f0e2131515"
        )
        UUID id,
        @ApiModelProperty(
                name = "name",
                dataType = "String",
                value = "Product name",
                example = "Flower"
        )
        String name,
        @ApiModelProperty(
                name = "description",
                dataType = "String",
                value = "Product description",
                example = "Beautiful red flower"
        )
        String description,
        @ApiModelProperty(
                name = "price",
                dataType = "float",
                value = "Product price",
                example = "44.4 USD"
        )
        String price,
        @ApiModelProperty(
                name = "quantity",
                dataType = "int",
                value = "Quantity of the product",
                example = "5"
        )
        int quantity,
        @ApiModelProperty(
                name = "star",
                dataType = "double",
                value = "Stars of the product",
                example = "0.0"
        )
        java.math.BigDecimal star,
        @ApiModelProperty(
                name = "image",
                dataType = "Object",
                value = "Product image",
                example = "{ 'name': 'flower.jpg', 'picture': 'base64image' }"
        )
        ImageResponse image,
        @ApiModelProperty(
                name = "characteristic",
                dataType = "Object",
                value = "Product characteristic",
                example = "{ 'size': 'S', 'color': 'black' }"
        )
        CharacteristicResponse characteristic,
        @ApiModelProperty(
                name = "createdBy",
                dataType = "Object",
                value = "User that created that product",
                example = "{ 'email': 'S@mail.com', 'firstName': 'black' }"
        )
        CreatedByResponse createdBy,
        @ApiModelProperty(
                name = "response",
                dataType = "Object",
                value = "Category of product",
                example = "{}"
        )
        CategoryResponse category) {
}

