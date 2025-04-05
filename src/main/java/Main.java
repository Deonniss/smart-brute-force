import ru.golovin.sbf.mask.*;

public class Main {

    public static void main(String[] args) {

        MaskGenerationOptionContainer container = MaskGenerationOptionContainer.builder()
                .keyWordBlockAmount(
                        MaskGenerationOption.builder()
                                .fix(-1)
                                .max(9)
                                .min(0)
                                .build()
                )
                .specialBlockAmount(
                        MaskGenerationOption.builder()
                                .fix(-1)
                                .max(4)
                                .min(0)
                                .build()
                )
                .dictionaryBlockAmount(
                        MaskGenerationOption.builder()
                                .fix(-1)
                                .max(5)
                                .min(0)
                                .build()
                )
                .build();

        VirtualThreadMaskGenerator virtualThread3MaskGenerator  = new VirtualThreadMaskGenerator(container);
    }
}
