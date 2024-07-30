package com.systex.mvcdemo1.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseForm {
    private Long id;
    private String name;
    private int amount;
}