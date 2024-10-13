package test;

public class test{

    static void foo(int i){
        i++;
    }

    public static void main(String[] args) {
        int i= 1;
        foo(i);
        foo(i);
        foo(i);
    }
}