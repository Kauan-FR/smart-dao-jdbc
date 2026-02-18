package com.kauanferreira.smartdaojdbc.application;

import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.entity.Seller;

import java.util.Date;

public class Program {
    static void main(String[] args) {

        // Test
        Department department = new Department(54, "Documents");

        Seller seller = new Seller(12, "Adam", "adam@gamil.com", 5000.00, new Date(), department);

        System.out.println(seller);

    }
}
