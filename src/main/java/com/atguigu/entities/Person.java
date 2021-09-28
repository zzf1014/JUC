package com.atguigu.juc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther zzyy
 * @create 2020-03-14 10:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person
{
    private Integer id;
    private String  personName;
}
