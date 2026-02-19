package com.kauanferreira.smartdaojdbc.application;

import com.kauanferreira.smartdaojdbc.dao.DaoFactory;
import com.kauanferreira.smartdaojdbc.dao.DepartmentDao;
import com.kauanferreira.smartdaojdbc.entity.Department;

import java.util.List;
import java.util.Scanner;

public class ProgramDepartment {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("| ==== | First test: department findById | ==== |");
        Department department = departmentDao.findById(1);
        System.out.println(department);

        System.out.println();

        System.out.println("| ==== | Third test: department findAll | ==== |");
        List<Department> departments = departmentDao.findAll();

        for (Department dep : departments) {
            System.out.println(dep);
        }

        System.out.println();

//        System.out.println("| ==== | Fourth test: department insert | ==== |");
//        Department newDepartment = new Department(null,"House");
//        departmentDao.insert(newDepartment);
//        System.out.printf("Insert new id = %d %n", newDepartment.getId());

        System.out.println();

        System.out.println("| ==== | Fifth test: department update | ==== |");
        System.out.println("Want to perform an UPDATE (yes/no)?");
        String resposta = sc.nextLine();

        if (resposta.equalsIgnoreCase("yes")) {
            for (Department dep : departments) {
                System.out.println(dep);
            }
            System.out.println("What id do you want to update ?");
            int id = sc.nextInt();
            sc.nextLine();
            department = departmentDao.findById(id);

            System.out.println("What is the new name of the department ?");
            String name = sc.nextLine();

            department.setName(name);
            departmentDao.update(department);
            System.out.println("Update Completed!!!");
        }

        System.out.println();
        sc.close();
    }
}
