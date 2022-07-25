package com.afj.solution.buyitapp.payload.request;

import java.io.Serializable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.afj.solution.buyitapp.model.enums.Currency;

import static com.afj.solution.buyitapp.common.Patterns.GSON;

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
    @Size(max = 255)
    private String name;

    @ApiModelProperty(
            name = "price",
            dataType = "float",
            value = "Price of the product",
            example = "44.4",
            required = true
    )
    @Digits(fraction = 2, integer = 10)
    private float price;

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
    @Size(max = 255)
    private String description;

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
