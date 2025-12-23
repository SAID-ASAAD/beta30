package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
