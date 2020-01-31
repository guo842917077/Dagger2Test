package com.baidu.crazyorange.usecusdagger2;

import com.baidu.crazyorange.customdagger2.annotation.Module;
import com.baidu.crazyorange.customdagger2.annotation.Provider;

@Module
public class StudentModule {
    @Provider
    public Student providerStudent() {
        return new Student();
    }
}
