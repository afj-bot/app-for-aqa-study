package com.afj.solution.buyitapp.payload.request;

import java.util.UUID;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Tomash Gombosh
 */
@ApiModel(value = "AddRatingRequest", description = "Add user rating")
public record AddRatingRequest(
        @ApiModelProperty(
                name = "id",
                dataType = "UUID",
                value = " Id of the order, product of user",
                example = "32323232s-",
                required = true
        )
        @NotNull
        UUID id,
        @ApiModelProperty(
                name = "stars",
                dataType = "int",
                value = "Stars of the product",
                example = "5",
                required = true
        )
        @Min(value = 1, message = "error.int.non-zero")
        @Max(value = 5, message = "error.int.max")
        int star
) {
}
