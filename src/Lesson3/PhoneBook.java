package Lesson3;

import java.util.ArrayList;
import java.util.HashMap;

class PersonNotFound extends Exception {
    public PersonNotFound() {
        super("Не найден контакт.");
    }

    public PersonNotFound(String name) {
        super("Не найден контакт: " + name);
    }
}

public class PhoneBook {

    HashMap<String, Person> phonebook = new HashMap<>();

    void addPerson(String name) {
        if (!phonebook.containsKey(name)) {
            phonebook.put(name, new Person());
        }
    }

    void addPerson(String name, String phone, String email) {
        addPerson(name);
        Person tempPerson = phonebook.get(name);
        tempPerson.addPhone(phone);
        tempPerson.addEmail(email);
    }

    void addPhone(String name, String phone) throws PersonNotFound {
        if (!phonebook.containsKey(name)) throw new PersonNotFound(name);
        phonebook.get(name).addPhone(phone);
    }

    void addEmail(String name, String email) throws PersonNotFound {
        if (!phonebook.containsKey(name)) throw new PersonNotFound(name);
        phonebook.get(name).addEmail(email);
    }

    ArrayList<String> getPhones(String name) throws PersonNotFound {
        if (!phonebook.containsKey(name)) throw new PersonNotFound(name);
        return phonebook.get(name).getPhones();
    }

    ArrayList<String> getEmails(String name) throws PersonNotFound {
        if (!phonebook.containsKey(name)) throw new PersonNotFound(name);
        return phonebook.get(name).getEmails();
    }

    @Override
    public String toString() {
        return "PhoneBook{" +
                "phonebook=" + phonebook +
                '}';
    }
}
