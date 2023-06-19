package com.Urban_India.batch;

import jakarta.validation.valueextraction.Unwrapping;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

@Component
public class ExceptionSkipPolicy implements SkipPolicy {
    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        return t instanceof NumberFormatException;
    }
}
