package com.hubt.th2501.product_service.controller.request;

import com.hubt.th2501.product_service.constant.Color;
import com.hubt.th2501.product_service.constant.SizeCharacter;
import com.hubt.th2501.product_service.constant.SizeNumber;
import com.hubt.th2501.product_service.validation.annotation.SizeConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SizeConstraint
public class SizeRequest {

    @Min(value = SizeNumber.SIZE_NUMBER_MIN, message = "Size must between 30 and 50")
    @Max(value = SizeNumber.SIZE_NUMBER_MAX, message = "Size must between 30 and 50")
    private Integer sizeNumber;

    private SizeCharacter sizeCharacter;

    @NotNull(message = "Color can not be null")
    private Color color;

    @NotNull(message = "Quantity can not be null")
    private Integer quantity;
}
