package com.nttdata.mscreditcard.service;

import com.nttdata.mscreditcard.model.CreditCardEntity;
import com.nttdata.mscreditcard.model.CustomerEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {

    Mono<CreditCardEntity> create(CreditCardEntity t);

    Flux<CreditCardEntity> findAll();

    Mono<CreditCardEntity> findById(String id);

    Mono<CreditCardEntity> update(CreditCardEntity t);

    Mono<Boolean> delete(String t);

    Mono<CustomerEntity> findCustomerById(String id);

    Flux<CreditCardEntity> findCreditCardByCustomer(String id);
}
