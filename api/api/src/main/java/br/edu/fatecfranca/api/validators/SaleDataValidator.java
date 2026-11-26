package br.edu.fatecfranca.api.validators;

import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import br.edu.fatecfranca.api.dtos.CarRequest;

public class SaleDataValidator extends RequestValidator<CarRequest> {

    public SaleDataValidator(RequestValidator<CarRequest> next) {
        super(next);
    }

    @Override
    protected void check(CarRequest request, Long carId) {
        boolean hasSellingDate = request.sellingDate() != null;
        boolean hasSellingPrice = request.sellingPrice() != null;

        if (hasSellingDate != hasSellingPrice) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "A data de venda e o preço de venda devem ser informados juntos ou ambos devem ficar ausentes."
            );
        }

        if (hasSellingPrice && request.sellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O preço de venda, quando informado, deve ser maior que zero."
            );
        }
    }
}
