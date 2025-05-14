package tests.model;

import java.util.List;

public class Student {
    public String name;
    public int age;
    public boolean isGoodStudent;
    public List<String> lessons;
    public Passport passport;

    public static class Passport {
        public int number;
        public String issueDate;
    }
}