package com.nttdata.mscreditcard.service.impl;

import com.nttdata.mscreditcard.model.CreditCardEntity;
import com.nttdata.mscreditcard.model.CustomerEntity;
import com.nttdata.mscreditcard.reporsitory.CreditCardRepository;
import com.nttdata.mscreditcard.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    WebClient webClient = WebClient.create("http://ms-api-gateway/api/ms-customer/customer/");

    @Autowired
    CreditCardRepository creditCardRepository;

    @Override
    public Mono<CreditCardEntity> create(CreditCardEntity t) {
        return creditCardRepository.save(t);
    }

    @Override
    public Flux<CreditCardEntity> findAll() {
        return creditCardRepository.findAll();
    }

    @Override
    public Mono<CreditCardEntity> findById(String id) {
        return creditCardRepository.findById(id);
    }

    @Override
    public Mono<CreditCardEntity> update(CreditCardEntity t) {
        return creditCardRepository.save(t);
    }

    @Override
    public Mono<Boolean> delete(String t) {
        return creditCardRepository.findById(t)
                .flatMap(credit -> creditCardRepository.delete(credit).then(Mono.just(Boolean.TRUE)))
                .defaultIfEmpty(Boolean.FALSE);
    }

    @Override
    public Mono<CustomerEntity> findCustomerById(String id) {
        return webClient.get().
                uri("/find/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CustomerEntity.class);
    }

    @Override
    public Flux<CreditCardEntity> findCreditCardByCustomer(String id) {
        return creditCardRepository.findByCustomerId(id);
    }

}
