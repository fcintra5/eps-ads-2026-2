package br.edu.fatecfranca.api.validators;

import java.time.Year;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.dtos.CarRequest;

public class ManufacturerYearValidator extends RequestValidator<CarRequest> {

    public ManufacturerYearValidator(RequestValidator<CarRequest> next) {
        super(next);
    }

    @Override
    protected void check(CarRequest request, Long carId) {
        if (request.yearManufacture() == null) {
            return;
        }

        int currentYear = Year.now().getValue();
        long year = request.yearManufacture();

        if (year < 1900 || year > currentYear) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O ano de fabricação deve estar entre 1900 e " + currentYear + "."
            );
        }
    }
}
