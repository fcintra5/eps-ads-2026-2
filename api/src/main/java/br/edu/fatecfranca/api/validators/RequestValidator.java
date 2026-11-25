package br.edu.fatecfranca.api.validators;

public abstract class RequestValidator<T> {

    private RequestValidator<T> next;

    public RequestValidator<T> setNext(RequestValidator<T> next) {
        this.next = next;
        return next;
    }

    public void validate(T request) {
        validateRequest(request);
        if (next != null) {
            next.validate(request);
        }
    }

    protected abstract void validateRequest(T request);
}
