package com.baidu.crazyorange.usecusdagger2.apt;

import com.baidu.crazyorange.customdagger2.Factory;
import com.baidu.crazyorange.usecusdagger2.Student;
import com.baidu.crazyorange.usecusdagger2.StudentModule;

public class StudentFactory implements Factory<Student> {
    private StudentModule studentModule;


    public StudentFactory(StudentModule studentModule) {
        this.studentModule = studentModule;
    }

    @Override
    public Student get() {
        return studentModule.providerStudent();
    }

    public static Factory<Student> create(StudentModule module) {
        return new StudentFactory(module);
    }
}
