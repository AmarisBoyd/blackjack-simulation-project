package com.amaris.blackjack_simulation_project.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<DataPlayer, Integer> {

}
