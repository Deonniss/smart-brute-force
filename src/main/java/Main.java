import ru.golovin.sbf.core.BruteforceGeneratorFacade;
import ru.golovin.sbf.core.SmartBruteforceGenerator;
import ru.golovin.sbf.core.SmartBruteforceGeneratorFacade;
import ru.golovin.sbf.core.gpu.aparapi.GpuAparapiBruteforceKernelExecutor;
import ru.golovin.sbf.mask.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {

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
                                .fix(1)
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
                .build();

        VirtualThreadMaskGenerator virtualThread3MaskGenerator = new VirtualThreadMaskGenerator(container);

        Set<List<MaskType>> masks = virtualThread3MaskGenerator.generate();
        System.out.println(masks);
        System.out.println(masks.size());

        BruteforceGeneratorFacade bruteforceGeneratorFacade = new SmartBruteforceGeneratorFacade(
                new SmartBruteforceGenerator(new GpuAparapiBruteforceKernelExecutor())
        );
        boolean result = bruteforceGeneratorFacade.generate(
                masks,
                Option.builder()
                        .keyWordOptions(Set.of(KeyWordOption.INJECT))
                        .specialBlockSize(SpecialOption.THREE)
                        .build(),
                "qwerty123"
        );
        System.out.println(result);
    }
}
