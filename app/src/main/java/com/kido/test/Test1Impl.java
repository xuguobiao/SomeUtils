package com.kido.test;

import com.kido.someutils.ClassFactory;

/**
 * @author Kido
 * @email everlastxgb@gmail.com
 * @create_time 2016/5/31 12:02
 */

public class Test1Impl implements ITest1 {
    ITest2 test2 = ClassFactory.create(Test2Impl.class);

    @Override
    public String getName() {
        return "Test1Impl";
    }
}
