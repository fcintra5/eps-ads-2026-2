package br.edu.fatecfranca.api.validators;

import java.math.BigDecimal;

import br.edu.fatecfranca.api.dtos.CarRequest;

public class SaleDataValidator extends RequestValidator<CarRequest> {

    @Override
    protected void validateRequest(CarRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Os dados do carro são obrigatórios.");
        }

        boolean hasDate = request.sellingDate() != null;
        boolean hasPrice = request.sellingPrice() != null;

        if (hasDate != hasPrice) {
            throw new IllegalArgumentException(
                    "sellingDate e sellingPrice devem ser informados juntos.");
        }

        if (hasPrice && request.sellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço de venda deve ser maior que zero.");
        }
    }
}
