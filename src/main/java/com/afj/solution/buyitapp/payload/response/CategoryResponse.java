package com.afj.solution.buyitapp.payload.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Tomash Gombosh
 */
@ApiModel(value = "CategoryResponse", description = "Category response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryResponse(
        @ApiModelProperty(
                name = "id",
                dataType = "UUID",
                value = "Category id",
                example = "7b8ccd07-ff4e-42cd-8815-96eba678cd68"
        )
        UUID id,
        @ApiModelProperty(
                name = "url",
                dataType = "String",
                value = "URL for filter",
                example = "7b8ccd07-ff4e-42cd-8815-96eba678cd68"
        )
        String url,
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
        String description,
        @ApiModelProperty(
                name = "subCategory",
                dataType = "Object",
                value = "Sub category",
                example = "{}"
        )
        SubCategoryResponse subCategory
        ) {
}
