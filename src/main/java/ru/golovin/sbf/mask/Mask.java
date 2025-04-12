package ru.golovin.sbf.mask;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Mask {

    private MaskType maskType;
    private Set<KeyWordOption> keyWordOptions;
    private SpecialOption specialBlockSize;
}
