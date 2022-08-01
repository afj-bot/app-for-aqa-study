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
@ApiModel(value = "CharacteristicResponse", description = "Product characteristicresponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacteristicResponse implements Serializable {

    private static final long serialVersionUID = 3628016844922383274L;

    @ApiModelProperty(
            name = "size",
            dataType = "String",
            value = "Size of the product",
            example = "S"
    )
    private String size;

    @ApiModelProperty(
            name = "color",
            dataType = "String",
            value = "Color of the product",
            example = "black"
    )
    private String color;

    @ApiModelProperty(
            name = "additionalParams",
            dataType = "String",
            value = "AdditionalParams of the product",
            example = "From Ukraine"
    )
    private String additionalParams;

    public CharacteristicResponse(final Consumer<CharacteristicResponse> builder) {
        requireNonNull(builder).accept(this);
    }
}
