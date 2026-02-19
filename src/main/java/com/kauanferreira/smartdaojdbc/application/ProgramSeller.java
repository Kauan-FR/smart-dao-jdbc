package com.kauanferreira.smartdaojdbc.application;

import com.kauanferreira.smartdaojdbc.dao.DaoFactory;
import com.kauanferreira.smartdaojdbc.dao.SellerDao;
import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.entity.Seller;

import java.util.List;
import java.util.Scanner;

/**
 * Test program for SellerDao operations.
 */

public class ProgramSeller {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("| ==== | First test: seller findById | ==== |");
        Seller seller = sellerDao.findById(1);
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

        System.out.println();

        System.out.println("| ==== | Fourth test: seller insert | ==== |");
        Seller newSeller = new Seller(null, "Jonath Doe", "jonat@gmail.com", 3000.0, new java.util.Date(), department);
        sellerDao.insert(newSeller);
        System.out.printf("Insert new id = %d %n", newSeller.getId());

        System.out.println();

        System.out.println("| ==== | Fifth test: seller update | ==== |");
        seller = sellerDao.findById(5);
        seller.setName("Adson Vargs");
        sellerDao.update(seller);
        System.out.println("Update completed");

        System.out.println();

        System.out.println("| ==== | Sixth test: seller delete | ==== |");
        System.out.println("Add the ID of the customer you want to delete");
        int id = sc.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete completed");

        sc.close();
    }
}
