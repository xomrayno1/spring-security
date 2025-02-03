package com.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.model.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long> {

}
