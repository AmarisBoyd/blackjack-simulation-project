package com.amaris.blackjack_simulation_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<DataPlayer, Integer> {

}
