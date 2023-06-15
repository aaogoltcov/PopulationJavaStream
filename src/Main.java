import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // количество несовершеннолетних
        long minorCount = persons
                .stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + minorCount);

        // список фамилий призывников
        List<String> conscripts = persons
                .stream()
                .filter(person -> {
                    final int age = person.getAge();

                    return age >= 18 && age < 27 && person.getSex() == Sex.MAN;
                })
                .map(Person::getFamily)
//                .limit(10)
                .toList();
        System.out.println("Список призывников: " + conscripts);

        // отсортированный по фамилии список потенциально работоспособных людей
        // с высшим образованием
        List<String> workForce = persons
                .stream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getSex() == Sex.MAN ? person.getAge() < 65 : person.getAge() < 60)
                .map(Person::getFamily)
                .sorted()
//                .limit(10)
                .toList();
        System.out.println("Список рабочей силы с высшим образованием: " + workForce);
    }
}