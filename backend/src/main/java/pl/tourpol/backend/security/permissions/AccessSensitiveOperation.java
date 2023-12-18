package pl.tourpol.backend.security.permissions;

import java.util.concurrent.Callable;
import java.util.function.LongFunction;

public interface AccessSensitiveOperation {

    <T> T callWithAccessCheck(Long id, LongFunction<T> callable, AccessSensitiveOperationType operationType);

    <T> T callWithAccessCheck(Long id, Callable<T> callable, AccessSensitiveOperationType operationType);
}
