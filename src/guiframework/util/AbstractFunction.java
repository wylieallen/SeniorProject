package guiframework.util;

public interface AbstractFunction {
    void execute();

    AbstractFunction NULL = () -> {};
}