package com.example.orm_exercise.controllers;

import com.example.orm_exercise.models.Address;
import com.example.orm_exercise.models.Contact;
import com.example.orm_exercise.repositories.AddressRepository;
import com.example.orm_exercise.repositories.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;

    public ContactController(ContactRepository contactRepository, AddressRepository addressRepository) {
        this.contactRepository = contactRepository;
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable int id) {
        return contactRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable int id, @RequestBody Contact updatedContact) {
        return contactRepository.findById(id).map(contact -> {
            contact.setName(updatedContact.getName());
            contact.setEmail(updatedContact.getEmail());
            contact.setPhoneNumber(updatedContact.getPhoneNumber());
            return contactRepository.save(contact);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable int id) {
        contactRepository.deleteById(id);
    }


    @PostMapping("/{id}/addresses")
    public ResponseEntity<Address> addAddress(@PathVariable int id, @RequestBody Address address) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if (contact == null) return ResponseEntity.notFound().build();
        address.setContact(contact);
        return ResponseEntity.ok(addressRepository.save(address));
    }

    @GetMapping("/{id}/addresses")
    public ResponseEntity<List<Address>> getAddresses(@PathVariable int id) {
        return ResponseEntity.ok(addressRepository.findByContactId(id));
    }
}