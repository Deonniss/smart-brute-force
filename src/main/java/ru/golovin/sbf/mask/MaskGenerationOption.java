package ru.golovin.sbf.mask;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MaskGenerationOption {

    private int max;
    private int min;
    private int fix;
}
