package br.edu.fatecfranca.api.validators;

import java.time.LocalDate;

import br.edu.fatecfranca.api.dtos.CarRequest;

public class ManufactureYearValidator extends RequestValidator<CarRequest> {

    @Override
    protected void validateRequest(CarRequest request) {
        if (request == null || request.yearManufacture() == null) {
            throw new IllegalArgumentException("O ano de fabricação é obrigatório.");
        }

        int currentYear = LocalDate.now().getYear();
        if (request.yearManufacture() < 1900 || request.yearManufacture() > currentYear) {
            throw new IllegalArgumentException(
                    "O ano de fabricação deve ser maior ou igual a 1900 e menor ou igual ao ano atual.");
        }
    }
}
