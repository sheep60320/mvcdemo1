package com.systex.mvcdemo1.controller;

import com.systex.mvcdemo1.controller.form.ExpenseForm;
import com.systex.mvcdemo1.entity.Expense;
import com.systex.mvcdemo1.exception.IDNotFoundEception;
import com.systex.mvcdemo1.repsitory.ExpenseCRUDRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class ExpenseController {
    private static final String LIST_ALL_EXPENSES = "expenses";
    private static final String ADD_MODIFY_EXPENSE_FORM = "expenseForm";
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
        if (!repository.findById(id).isPresent()) {
            throw new IDNotFoundEception(id);
        }
        repository.deleteById(id);
        return "redirect:/records/all";
    }

    @GetMapping("/records/add")
    public String showAdd(Model model) {
        ExpenseForm f = new ExpenseForm();
        model.addAttribute(ADD_MODIFY_EXPENSE_FORM, f);
        return "records/add";
    }

    @PostMapping("/records/add")
    public String storeAdd(ExpenseForm f) {
        log.info("get a form as:{}", f);
        Expense e = new Expense();
        BeanUtils.copyProperties(f, e);
        repository.save(e);
        return "redirect:/records/all";
    }

    @GetMapping("/records/modify")
    public String showModify(@RequestParam Long id, Model model) {
        if (repository.findById(id).isEmpty()) {
            throw new IDNotFoundEception(id);
        }
        // load data from db
        Expense expense = repository.findById(id).get();
        ExpenseForm f = new ExpenseForm();
        // entity ==> form
        BeanUtils.copyProperties(expense, f);
        model.addAttribute(ADD_MODIFY_EXPENSE_FORM, f);
        return "records/modify";
    }

    @PostMapping("/records/modify")
    public String storeModify(ExpenseForm f) {
        // form==>entity==>db
        Expense expense = repository.findById(f.getId()).get();
        BeanUtils.copyProperties(f,expense);
        repository.save(expense);
        return "redirect:/records/all";
    }


}