package com.afj.solution.buyitapp.payload.request;

import java.io.Serializable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.afj.solution.buyitapp.model.enums.Currency;

import static com.afj.solution.buyitapp.constans.Patterns.GSON;

/**
 * @author Tomash Gombosh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CreateProductRequest", description = "Create product request model")
public class CreateProductRequest implements Serializable {

    private static final long serialVersionUID = 5221455932434649938L;

    @ApiModelProperty(
            name = "name",
            dataType = "String",
            value = "Product name",
            example = "Flowers",
            required = true
    )
    @NotEmpty
    @Size(max = 255, message = "error.value.string.max")
    private String name;

    @ApiModelProperty(
            name = "price",
            dataType = "float",
            value = "Price of the product",
            example = "44.4",
            required = true
    )
    @Digits(fraction = 2, integer = 10, message = "error.value.float")
    private float price;

    @ApiModelProperty(
            name = "quantity",
            dataType = "int",
            value = "Quantity of the product",
            example = "5",
            required = true
    )
    @Min(value = 1, message = "error.value.non-zero")
    @Max(value = 8_388_607, message = "error.value.max")
    private int quantity;

    @ApiModelProperty(
            name = "currency",
            dataType = "Currency",
            value = "Currency of the product, by default USD",
            example = "USD"
    )
    private Currency currency = Currency.USD;

    @ApiModelProperty(
            name = "description",
            dataType = "String",
            value = "Product description",
            example = "Beautiful red flower"
    )
    @Size(max = 255, message = "error.value.string.max")
    private String description;

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
