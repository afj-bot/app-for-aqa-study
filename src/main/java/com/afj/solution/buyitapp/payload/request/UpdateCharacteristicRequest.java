package com.afj.solution.buyitapp.payload.request;

import java.io.Serializable;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.afj.solution.buyitapp.constans.Patterns.GSON;

/**
 * @author Tomash Gombosh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UpdateCharacteristicRequest", description = "Update characteristic request")
public class UpdateCharacteristicRequest implements Serializable {

    private static final long serialVersionUID = -8646541984538999467L;

    @ApiModelProperty(
            name = "size",
            dataType = "String",
            value = "Size of the product",
            example = "S"
    )
    @Size(max = 255)
    private String size;

    @ApiModelProperty(
            name = "color",
            dataType = "String",
            value = "Color of the product",
            example = "black"
    )
    @Size(max = 255)
    private String color;

    @ApiModelProperty(
            name = "additionalParams",
            dataType = "String",
            value = "AdditionalParams of the product",
            example = "From Ukraine"
    )
    @Size(max = 255)
    private String additionalParams;

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
