package com.accenture.desafio.services;

import com.accenture.desafio.objects.Phone;
import com.accenture.desafio.repositories.PhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PhoneService {
    private PhoneRepository phoneRepository;

    public void savePhones(List<Phone> phoneList) {
        phoneList.forEach(phone -> phoneRepository.save(phone));
    }
}
