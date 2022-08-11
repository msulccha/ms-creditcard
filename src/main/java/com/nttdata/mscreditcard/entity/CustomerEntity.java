package com.nttdata.mscreditcard.entity;

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


