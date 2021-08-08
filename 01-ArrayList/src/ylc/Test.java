package ylc;

public class Test {
    public static void main(String[] args) {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person("jack", 12));
        persons.add(null);
        persons.add(new Person("rose", 11));
        persons.add(null);
        System.out.println(persons.indexOf(null));
    }


}
