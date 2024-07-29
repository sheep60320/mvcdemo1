package com.systex.mvcdemo1.controller;

import com.systex.mvcdemo1.controller.form.ExpenseForm;
import com.systex.mvcdemo1.entity.Expense;
import com.systex.mvcdemo1.repsitory.ExpenseCRUDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExpenseController {
    private static final String LIST_ALL_EXPENSES = "expenses";
    private static final String ADD_EXPENSE_FORM = "expenseForm";
    @Autowired
    private ExpenseCRUDRepository repository;

    @GetMapping("/records/all")
    public String listAll(Model model) {
        Iterable<Expense> expenses = repository.findAll();
//        List<Expense> expenses = new ArrayList<>();
//        repository.findAll().forEach(expense -> expenses.add(expense));
        model.addAttribute(LIST_ALL_EXPENSES, expenses);
        return "records/list";
    }

    @GetMapping("/records/delete")
    public String delete(@RequestParam Long id) {
        repository.deleteById(id);
        return "redirect:/records/all";
    }

    @GetMapping("/records/add")
    public String showAdd(Model model) {
        ExpenseForm f = new ExpenseForm();
        f.setAmount(888);
        f.setName("Tim");
        model.addAttribute(ADD_EXPENSE_FORM, f);
        return "records/add";
    }
}