package ru.golovin.sbf.mask;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaskGenerationOptionContainer {

    private final MaskGenerationOption passwordLength;
    private final MaskGenerationOption keyWordBlockAmount;
    private final MaskGenerationOption specialBlockAmount;
    private final MaskGenerationOption dictionaryBlockAmount;
}
