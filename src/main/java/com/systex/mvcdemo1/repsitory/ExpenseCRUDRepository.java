package com.systex.mvcdemo1.repsitory;

import com.systex.mvcdemo1.entity.Expense;
import org.springframework.data.repository.CrudRepository;



public interface ExpenseCRUDRepository extends CrudRepository<Expense, Long> {
}