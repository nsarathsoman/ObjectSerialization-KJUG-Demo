package kjug;

public class ConstructorNotFoundException extends RuntimeException{
    public ConstructorNotFoundException(Throwable e) {
        super(e);
    }

    public ConstructorNotFoundException(){}
}
