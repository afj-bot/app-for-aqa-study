package com.afj.solution.buyitapp.payload.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.afj.solution.buyitapp.constans.Patterns.GSON;

/**
 * @author Tomash Gombosh
 */
@ApiModel(value = "AddRatingRequest", description = "Add user rating")
public record AddRatingRequest(
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
    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
