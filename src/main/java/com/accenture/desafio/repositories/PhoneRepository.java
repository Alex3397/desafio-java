package com.accenture.desafio.repositories;

import com.accenture.desafio.objects.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, String>  {
}
