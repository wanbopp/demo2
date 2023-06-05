package com.example.delete.controller;

import com.example.delete.entity.Employee;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 测试BindResult和@Valid配合使用 用于数据数据校验，基于对象
 */
@RequestMapping("/employee")
@RestController
public class Demo {
    //换行符
    private static String lineSeparator = System.lineSeparator();


    @PostMapping("/bindingResult")
    public Object test(@RequestBody @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            StringBuffer buffer = new StringBuffer();
            fieldErrors.forEach(fieldError -> {buffer.append(fieldError.getDefaultMessage()+lineSeparator);//buffer.append()追加
            });
            buffer.insert(0,"use n @Valid n BindingResult: "+lineSeparator);//buffer.insert()  在指定位置插入
return buffer.toString();
        }
        return "职员添加成功"+employee.toString();
    }

}
