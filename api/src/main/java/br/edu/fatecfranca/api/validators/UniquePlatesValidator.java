package br.edu.fatecfranca.api.validators;

import br.edu.fatecfranca.api.dtos.CarRequest;
import br.edu.fatecfranca.api.repositories.CarRepository;

public class UniquePlatesValidator extends RequestValidator<CarRequest> {

    private final CarRepository repository;

    public UniquePlatesValidator(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void validateRequest(CarRequest request) {
        if (request == null || request.plates() == null || request.plates().isBlank()) {
            throw new IllegalArgumentException("A placa é obrigatória.");
        }

        if (repository.existsByPlates(request.plates())) {
            throw new IllegalArgumentException("Já existe um carro cadastrado com a placa informada.");
        }
    }
}
