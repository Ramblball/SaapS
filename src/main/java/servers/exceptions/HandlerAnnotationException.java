package servers.exceptions;

public class HandlerAnnotationException extends Exception {
    public HandlerAnnotationException(String className, Throwable cause) {
        super("class " + className + " cannot be annotated as @Handler", cause);
    }
}
