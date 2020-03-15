package Lesson3;

import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        String testString = "Возвышавшийся перед оркестром человек во фраке, увидев Маргариту, побледнел, " +
                "заулыбался и вдруг взмахом рук поднял весь оркестр. Ни на мгновение не прерывая музыки, " +
                "оркестр, стоя, окатывал Маргариту звуками. Человек над оркестром отвернулся от него и" +
                " поклонился низко, широко разбросив руки, и Маргарита, улыбаясь, помахала ему рукой.";

        HashSet<String> uniqueWords = getUniqueWords(testString);
        System.out.println("Уникальные слова:");
        System.out.println(uniqueWords);
        HashMap<String, Integer> statistics = getWordsStatistics(testString);
        System.out.println("\nСтатистика встречаемости слов:");
        System.out.println(statistics);

        System.out.println();
        System.out.println("Добавляем контакты в записную книжку:");
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addPerson("Билл Гейтс", "234-1743-3478", "billy@microsoft.com");
        phoneBook.addPerson("Марк Цукерберг", "7946-78-123", "mark@facebook.com");
        phoneBook.addPerson("Эштон Катчер");
        try {
            phoneBook.addPhone("Эштон Катчер", "345-464-7879");
            phoneBook.addEmail("Эштон Катчер", "ashton@hollywood.com");
            phoneBook.addEmail("Эрштон Катчер", "ashton123@holly.com");
        } catch (PersonNotFound e) {
            System.out.println(e.getMessage());
        }
        phoneBook.addPerson("Арнольд Шварцнеггер", "654-457-78", "guber@california.com");
        phoneBook.addPerson("Сильвестр Сталлоне", "348-789-3457", "rocky@gmail.com");

        phoneBook.addPerson("Никита Михалков", "777-888-9999", "rusfilm@mail.ru");
        phoneBook.addPerson("Магнус Карлсен", "123-456-7890", "chessgod@chess.com");
        phoneBook.addPerson("Александр Овечкин", "987-654-3210", "shipper@yandex.ru");


        try {
            phoneBook.addEmail("Никита Михалков", "nashekino@yandex.ru");
            phoneBook.addPhone("Магнус Карлсен", "333-555-7777");
            phoneBook.addPhone("Магнус Карлсен", "555-777-9999");
            phoneBook.addPhone("Сильвестр Сталлоне", "332-42-3234-2");
            phoneBook.addEmail("Сильвестр Сталлоне", "rambo@yahoo.com");
            phoneBook.addEmail("Магнус Карлсон", "na_kryshe@domа.se");
        } catch (PersonNotFound e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Осуществляем поиск сведений по имени контакта:");
        try {
            System.out.println("Почтовые адреса Никиты Михалкова: " + phoneBook.getEmails("Никита Михалков"));
            System.out.println("Почтовые адреса Сильвестра Сталлоне: " + phoneBook.getEmails("Сильвестр Сталлоне"));
            System.out.println("Телефоны Сильвестра Сталлоне: " + phoneBook.getPhones("Сильвестр Сталлоне"));
            System.out.println("Телефоны Магнуса Карлсена: " + phoneBook.getPhones("Магнус Карлсен"));
            System.out.println("Телефоны Антонио Бандераса: " + phoneBook.getPhones("Антонио Бандерас"));
        } catch (PersonNotFound e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nТелефонная книга полностью:\n");
        System.out.println(phoneBook);


    }

    public static HashSet<String> getUniqueWords(String text) {
        HashSet<String> resultSet = new HashSet<>();
        String[] words = text.split("[^A-Za-zА-Яа-я]+");
        for (String str : words) {
            resultSet.add(str.toLowerCase());
        }
        return resultSet;
    }

    public static HashMap<String, Integer> getWordsStatistics(String text) {
        HashMap<String, Integer> resultMap = new HashMap<>();
        String[] words = text.split("[^A-Za-zА-Яа-я]+");
        for (String str : words) {
            Integer freq = resultMap.get(str.toLowerCase());
            resultMap.put(str.toLowerCase(), freq == null ? 1 : freq + 1);
        }
        return resultMap;
    }
}

