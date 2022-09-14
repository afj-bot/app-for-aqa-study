package com.afj.solution.buyitapp.payload.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Tomash Gombosh
 */
@ApiModel(value = "SubCategoryResponse", description = "Sub category response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SubCategoryResponse(
        @ApiModelProperty(
                name = "id",
                dataType = "UUID",
                value = "Sub category id",
                example = "7b8ccd07-ff4e-42cd-8815-96eba678cd68"
        )
        UUID id,
        @ApiModelProperty(
                name = "name",
                dataType = "String",
                value = "Short name",
                example = "cars"
        )
        String name,
        @ApiModelProperty(
                name = "description",
                dataType = "String",
                value = "Short description",
                example = "Cars category"
        )
        String description
) {
}
