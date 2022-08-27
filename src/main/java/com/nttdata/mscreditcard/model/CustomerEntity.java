package com.nttdata.mscreditcard.model;

import lombok.Data;

@Data
public class CustomerEntity {

        String id;

        String name;

        String lastName;

        TypeCustomerEntity typeCustomer;

        String document;

        Integer age;

    }


