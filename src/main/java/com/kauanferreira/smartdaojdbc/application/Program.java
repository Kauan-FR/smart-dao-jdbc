package com.kauanferreira.smartdaojdbc.application;

import com.kauanferreira.smartdaojdbc.dao.DaoFactory;
import com.kauanferreira.smartdaojdbc.dao.SellerDao;
import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.entity.Seller;

import java.util.Date;
import java.util.List;

/**
 * Test program for SellerDao operations.
 */

public class Program {
    static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("| ==== | First test: seller findById | ==== |");
        Seller seller = sellerDao.findById(2);
        System.out.println(seller);

        System.out.println();

        System.out.println("| ==== | Second test: seller findByDepartment | ==== |");
        Department department = new Department(2, null);
        List<Seller> listaSeller = sellerDao.findByDepartment(department);

        for (Seller obj : listaSeller) {
            System.out.println(obj);
        }

        System.out.println();

        System.out.println("| ==== | Third test: seller findAll | ==== |");
        listaSeller = sellerDao.findAll();

        for (Seller obj : listaSeller) {
            System.out.println(obj);
        }

    }
}
