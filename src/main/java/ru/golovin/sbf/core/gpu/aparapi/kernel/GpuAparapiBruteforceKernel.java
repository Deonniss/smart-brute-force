package ru.golovin.sbf.core.gpu.aparapi.kernel;

import com.aparapi.Kernel;

public abstract class GpuAparapiBruteforceKernel extends Kernel {

    public abstract void setOffset(long offset);
}
