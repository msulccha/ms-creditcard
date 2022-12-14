package com.nttdata.mscreditcard.controller;

import com.nttdata.mscreditcard.model.CreditCardEntity;
import com.nttdata.mscreditcard.model.CustomerEntity;
import com.nttdata.mscreditcard.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("creditcard")
@Slf4j
public class CreditCardController {

    @Autowired
    CreditCardService creditCardService;

    @GetMapping("list")
    public Flux<CreditCardEntity> findAll(){
        return creditCardService.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<CreditCardEntity> findById(@PathVariable String id){
        return creditCardService.findById(id);
    }

    @GetMapping("/findCreditCards/{id}")
    public Flux<CreditCardEntity> findCreditCardByCustomer(@PathVariable String id){
        return creditCardService.findCreditCardByCustomer(id);
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<CreditCardEntity>> create(@RequestBody CreditCardEntity creditCard){

        return creditCardService.findCustomerById(
                        creditCard.getCustomer().getId()).
                flatMap(c -> {
                    creditCard.setCustomer(c);
                    creditCard.setDate(LocalDateTime.now());
                    creditCard.setExpirationDate(LocalDate.now().plusMonths(6));
                    return creditCardService.create(creditCard);
                })
                .map(savedCustomer -> new ResponseEntity<>(savedCustomer , HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<CreditCardEntity>> update(@RequestBody CreditCardEntity creditCard) {
        return creditCardService.findById(creditCard.getId()) // REVISO SI EXISTE LA TARJETA DE CREDITO
                .flatMap(cc -> creditCardService.findCustomerById(creditCard.getCustomer().getId())
                        .flatMap(customer -> {
                            creditCard.setCustomer(customer);
                            return creditCardService.update(creditCard);
                        }))
                .map(cc->new ResponseEntity<>(cc , HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        //        return creditCardService.update(c)
        //        .map(savedCustomer -> new ResponseEntity<>(savedCustomer, HttpStatus.CREATED))
        //        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable String id) {
        return creditCardService.delete(id)
                .filter(deleteCustomer -> deleteCustomer)
                .map(deleteCustomer -> new ResponseEntity<>("CreditCard Deleted", HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // Testeo **********************************************
    @GetMapping("/findByCustomer/{id}")
    public Mono<CustomerEntity> findCustomerById(@PathVariable String id){
        return creditCardService.findCustomerById(id);
    }





}
