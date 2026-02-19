package com.kauanferreira.smartdaojdbc.application;

import com.kauanferreira.smartdaojdbc.dao.DaoFactory;
import com.kauanferreira.smartdaojdbc.dao.DepartmentDao;
import com.kauanferreira.smartdaojdbc.entity.Department;

import java.util.Scanner;

public class ProgramDepartment {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("| ==== | First test: department findById | ==== |");
        Department department = departmentDao.findById(1);
        System.out.println(department);

        System.out.println();
        sc.close();
    }
}
