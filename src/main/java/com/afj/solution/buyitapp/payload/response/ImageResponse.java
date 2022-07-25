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
@ApiModel(value = "ImageResponse", description = "Product image response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageResponse implements Serializable {

    private static final long serialVersionUID = -8807326600668589292L;

    @ApiModelProperty(
            name = "name",
            dataType = "String",
            value = "Image name",
            example = "flower.jpg"
    )
    private String name;

    @ApiModelProperty(
            name = "picture",
            dataType = "byte[]",
            value = "Image base64 array"
    )
    private byte[] picture;

    public ImageResponse(final Consumer<ImageResponse> builder) {
        requireNonNull(builder).accept(this);
    }
}
