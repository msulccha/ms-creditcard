package com.nttdata.mscreditcard.reporsitory;

import com.nttdata.mscreditcard.model.CreditCardEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CreditCardRepository  extends ReactiveMongoRepository<CreditCardEntity, String> {

    Flux<CreditCardEntity> findByCustomerId(String id);

}
