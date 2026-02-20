package com.kauanferreira.smartdaojdbc.application;

import com.kauanferreira.smartdaojdbc.dao.DaoFactory;
import com.kauanferreira.smartdaojdbc.dao.SellerDao;
import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.entity.Seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Test program for SellerDao operations.
 */

public class ProgramSeller {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller;
        Department department = new Department();
        List<Seller> sellers;
        String response;

        System.out.println("| ==== | First test: seller findById | ==== |");
        seller = sellerDao.findById(1);
        System.out.println(seller);

        System.out.println();

        System.out.println("| ==== | Second test: seller findByDepartment | ==== |");
        System.out.println("Do you want to search for a seller by department ID (yes/no)?");
        response = sc.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.println("What is the department ID?");
            int id = sc.nextInt();
            sc.nextLine();
            department.setId(id);

            sellers = sellerDao.findByDepartment(department);

            for (Seller obj : sellers) {
                System.out.println(obj);
            }
        }

        System.out.println();

        System.out.println("| ==== | Third test: seller findAll | ==== |");
        sellers = sellerDao.findAll();

        for (Seller obj : sellers) {
            System.out.println(obj);
        }

        System.out.println();

        System.out.println("| ==== | Fourth test: seller insert | ==== |");
        System.out.println("Do you want to insert a seller into the seller table (yes/no)?");
        response = sc.nextLine();
        
        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Do you want to insert a seller into the seller table?");
            String name = sc.next();
            sc.nextLine();
            seller.setName(name);
            
            System.out.println("What will be the vendor's email?");
            String email = sc.next();
            sc.nextLine();
            seller.setEmail(email);
            
            System.out.println("What will be the base salary of the salesperson?");
            double baseSalary = sc.nextDouble();
            sc.nextLine();
            seller.setBaseSalary(baseSalary);

            try {
                System.out.println("What is the seller's date of birth?");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String birthDate = sc.next();
                sc.nextLine();
                seller.setBirthDate(sdf.parse(birthDate));
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            seller.setDepartment(department);

            sellerDao.insert(seller);
            System.out.printf("Insert new id = %d %n", seller.getId());

        }

        System.out.println();

        System.out.println("| ==== | Fifth test: seller update | ==== |");
        System.out.println();
        System.out.println("Do you want to make an update on any seller?");
        response = sc.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            seller = sellerDao.findById(5);
            seller.setName("Adson Vargs");
            sellerDao.update(seller);
            System.out.println("Update completed");
        }

        System.out.println();

        System.out.println("| ==== | Sixth test: seller delete | ==== |");
        System.out.println();
        System.out.println("Do you want to delete any seller?");
        response = sc.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Add the ID of the customer you want to delete");
            int id = sc.nextInt();
            sellerDao.deleteById(id);
            System.out.println("Delete completed");
        }

        System.out.println();

        System.out.println("| ==== | Seven test: seller findByName | ==== |");
        System.out.println();
        System.out.println("Do you want to search for a seller by name (yes/no)?");
        response = sc.nextLine();
        
        if (response.equalsIgnoreCase("yes")) {
            System.out.println("What is the name of the seller?");
            String name = sc.nextLine();
            seller.setName(name);
            sellerDao.findByName(name);
            System.out.println(seller);
            System.out.println("Seller found!!!");
        }
        sc.close();
    }
}
