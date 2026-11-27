package br.edu.fatecfranca.api.validators;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.dtos.CarRequest;
import br.edu.fatecfranca.api.repositories.CarRepository;

public class UniquePlatesValidator extends RequestValidator<CarRequest> {

    private final CarRepository repository;

    public UniquePlatesValidator(CarRepository repository,
                                 RequestValidator<CarRequest> next) {
        super(next);
        this.repository = repository;
    }

    @Override
    protected void check(CarRequest request, Long carId) {
        boolean alreadyExists;

        if (carId == null) {
            alreadyExists = repository.existsByPlates(request.plates());
        } else {
            alreadyExists = repository.existsByPlatesAndIdNot(
                request.plates(), carId
            );
        }

        if (alreadyExists) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "No outro carro tem a mesma placa."
            );
        }
    }
}
