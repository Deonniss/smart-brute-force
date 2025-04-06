import ru.golovin.sbf.core.BruteforceGeneratorFacade;
import ru.golovin.sbf.core.SmartBruteforceGenerator;
import ru.golovin.sbf.core.SmartBruteforceGeneratorFacade;
import ru.golovin.sbf.mask.*;

import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        MaskGenerationOptionContainer container = MaskGenerationOptionContainer.builder()
                .keyWordBlockAmount(
                        MaskGenerationOption.builder()
                                .fix(-1)
                                .max(0)
                                .min(0)
                                .build()
                )
                .specialBlockAmount(
                        MaskGenerationOption.builder()
                                .fix(-1)
                                .max(0)
                                .min(0)
                                .build()
                )
                .dictionaryBlockAmount(
                        MaskGenerationOption.builder()
                                .fix(1)
                                .max(0)
                                .min(0)
                                .build()
                )
                .options(Set.of(KeyWordOption.INJECT))
                .build();

        VirtualThreadMaskGenerator virtualThread3MaskGenerator = new VirtualThreadMaskGenerator(container);

        Set<List<Mask>> masks = virtualThread3MaskGenerator.generate();
        System.out.println(masks);
        System.out.println(masks.size());


        BruteforceGeneratorFacade bruteforceGeneratorFacade = new SmartBruteforceGeneratorFacade(
                new SmartBruteforceGenerator()
        );
        List<String> result = bruteforceGeneratorFacade.generate(masks);
        System.out.println(result);
        System.out.println(result.size());


    }
}
