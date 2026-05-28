package org.example.ex3.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.unbescape.xml.XmlEscape;

import java.lang.reflect.Member;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
public class OrderCreateDto {

    @NotBlank(message = "Name must be not Empty")
    private String customerName;
    @NotBlank(message = "Product must be not Empty")
    private String product;

    @NotNull(message = "Quantity must be not Empty")
    @Min(value = 1, message = "Quantity must be greater than 0")

    private Integer quantity;

    @NotNull(message = "Total amount must not be empty")

    @Min(value = 1, message = "Total amount must be greater than 0")

    private Double totalAmount;

}
