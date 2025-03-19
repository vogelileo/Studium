package webFramework;

import java.lang.reflect.Method;

public record WebHandler<T>(Method method, T instance, String contentType) {

}
