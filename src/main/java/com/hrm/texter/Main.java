package com.hrm.texter;

public class Main {
    public static void main(String[] args) {
        BeanFactory.getInstance().getBean(Application.class).run(args);
    }
}
