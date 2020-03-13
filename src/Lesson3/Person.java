package Lesson3;

import java.util.ArrayList;

public class Person {
    private ArrayList<String> phones = new ArrayList<>();
    private ArrayList<String> emails = new ArrayList<>();

    public void addPhone(String phone) {
        if (!phones.contains(phone)) phones.add(phone);
    }

    public void addEmail(String email) {
        if (!emails.contains(email)) emails.add(email);
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    @Override
    public String toString() {
        return "Person{" +
                "phones=" + phones +
                ", emails=" + emails +
                '}';
    }
}
