package fr.itarverne.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
interface CountryRepository extends JpaRepository<Country, Long> {

}