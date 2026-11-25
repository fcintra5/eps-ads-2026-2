package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.dtos.CarRequest;
import br.edu.fatecfranca.api.entities.Car;
import br.edu.fatecfranca.api.entities.Customer;
import br.edu.fatecfranca.api.repositories.CarRepository;
import br.edu.fatecfranca.api.repositories.CustomerRepository;
import br.edu.fatecfranca.api.validators.ManufactureYearValidator;
import br.edu.fatecfranca.api.validators.RequestValidator;
import br.edu.fatecfranca.api.validators.SaleDataValidator;
import br.edu.fatecfranca.api.validators.UniquePlatesValidator;

@Service
public class CarService {

   private final CarRepository repository;
   private final CustomerRepository customerRepository;
   private final RequestValidator<CarRequest> createValidator;
   private final RequestValidator<CarRequest> updateValidator;

   public CarService(CarRepository repository, CustomerRepository customerRepository) {
       this.repository = repository;
       this.customerRepository = customerRepository;

       ManufactureYearValidator createYearValidator = new ManufactureYearValidator();
       SaleDataValidator createSaleValidator = new SaleDataValidator();
       UniquePlatesValidator platesValidator = new UniquePlatesValidator(repository);
       createYearValidator.setNext(createSaleValidator);
       createSaleValidator.setNext(platesValidator);
       this.createValidator = createYearValidator;

       ManufactureYearValidator updateYearValidator = new ManufactureYearValidator();
       SaleDataValidator updateSaleValidator = new SaleDataValidator();
       updateYearValidator.setNext(updateSaleValidator);
       this.updateValidator = updateYearValidator;
   }

   private void copyToEntity(CarRequest request, Car car) {
       car.setBrand(request.brand());
       car.setModel(request.model());
       car.setColor(request.color());
       car.setYearManufacture(request.yearManufacture().intValue());
       car.setImported(request.imported());
       car.setPlates(request.plates());
       car.setSellingDate(request.sellingDate());
       car.setSellingPrice(request.sellingPrice());

       if (request.customerId() != null) {
           Customer customer = customerRepository.findById(request.customerId())
                   .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
           car.setCustomer(customer);
       } else {
           car.setCustomer(null);
       }
   }

   public Car create(CarRequest request) {
       createValidator.validate(request);
       Car car = new Car();
       copyToEntity(request, car);
       return repository.save(car);
   }

   public List<Car> findAll() {
       return repository.findAll();
   }

   public Optional<Car> findById(Long id) {
       return repository.findById(id);
   }

   public Car update(Long id, CarRequest request) {
       Car car = repository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Carro não encontrado."));

       updateValidator.validate(request);

       if (repository.existsByPlatesAndIdNot(request.plates(), id)) {
           throw new IllegalArgumentException("Já existe um carro cadastrado com a placa informada.");
       }

       copyToEntity(request, car);
       car.setId(id);
       return repository.save(car);
   }

   public boolean existsById(Long id) {
       return repository.existsById(id);
   }

   public void deleteById(Long id) {
       repository.deleteById(id);
   }
}