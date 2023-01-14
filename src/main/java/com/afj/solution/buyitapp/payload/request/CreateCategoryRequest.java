package com.afj.solution.buyitapp.payload.request;

import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.afj.solution.buyitapp.constans.Patterns.GSON;

/**
 * @author Kristian Gombosh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CreateCategoryRequest", description = "Create category request model")
public class CreateCategoryRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 5221455932434649938L;

    @ApiModelProperty(
            name = "name",
            dataType = "String",
            value = "Category name",
            example = "Shoes",
            required = true
    )
    @NotEmpty
    private String name;

    @ApiModelProperty(
            name = "description",
            dataType = "String",
            value = "Category description",
            example = "The Shoes category"
    )
    @Size(max = 128, message = "error.value.string.max")
    private String description;

    @ApiModelProperty(
            name = "subCategoryName",
            dataType = "String",
            value = "Sub-category name",
            example = "other",
            required = true
    )
    @NotEmpty
    private String subCategoryName;

    @ApiModelProperty(
            name = "subCategoryDescription",
            dataType = "String",
            value = "Sub-category description",
            example = "Other",
            required = true
    )
    @NotEmpty
    private String subCategoryDescription;

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
