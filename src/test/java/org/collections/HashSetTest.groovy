package org.collections

import org.dto.Person
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class HashSetTest extends Specification {

    HashSet<Person> persons
    @Shared
    Person jan = new Person("Jan", "Nowak", 20)
    @Shared
    Person janBis = new Person("Jan", "Nowak", 20)
    @Shared
    Person piotr = new Person("Piotr", "Kowalski", 30)
    @Shared
    Person adam = new Person("Adam", "Wiśniewski", 40)
    @Shared
    Person krzysztof = new Person("Krzysztof", "Wójcik", 50)

    void setup() {
        persons = new HashSet()
    }

    void cleanup() {
        persons = new HashSet()
    }

    def "empty set size"() {
        expect:
        persons.size().equals(0)
    }

    def "Size"() {
        when:
        persons.add(jan)
        persons.add(piotr)

        then:
        persons.size().equals(2)
    }

    def "IsEmpty"() {
        expect:
        persons.isEmpty() == true
    }

    def "Is Not Empty"() {
        when:
        persons.add(jan)

        then:
        persons.isEmpty() == false
    }

    @Unroll
    def "Contains #person"() {

        when:
        persons.add(jan)

        then:
        persons.contains(person).equals(result)

        where:
        person | result
        jan    | true
        janBis | true
        piotr  | false

    }

    def "Iterator"() {

        given:
        def List tab = [jan, janBis, piotr, adam, krzysztof]
        def collected = new ArrayList();
        persons.addAll(tab)

        when:
        for (Person p : persons) {
            collected.add(p)
        }

        then:
        collected.size() == 4
        collected.containsAll(tab)
        tab.containsAll(collected)
        collected.containsAll(persons)
        tab.containsAll(persons)
    }

    def "ToArray"() {
        given:
        persons.add(jan)
        persons.add(piotr)

        when:
        def tab = persons.toArray()

        then:
        tab.contains(jan)
        tab.contains(janBis)
        tab.contains(piotr)

        tab.contains(adam) == false
        tab.contains(krzysztof) == false
    }

    def "Add"() {

        when:
        persons.add(jan)

        then:
        persons.contains(jan)
        persons.contains(janBis)
        persons.contains(piotr) == false
        persons.size() == 1
    }

    def "Remove"() {
        given:
        persons.addAll([jan, piotr])

        when:
        persons.remove(piotr)

        then:
        persons.containsAll([jan, janBis])
        persons.contains(piotr) == false
    }

    def "AddAll"() {

        when:
        persons.addAll([jan, piotr])

        then:
        persons.containsAll([jan, janBis, piotr])
        persons.contains(adam) == false
    }

    def "Clear"() {
        given:
        persons.addAll([jan, janBis, piotr])

        when:
        persons.clear()

        then:
        persons.isEmpty()
        persons.size() == 0
        persons.contains(jan) == false
        persons.contains(piotr) == false
    }

    def "RemoveAll"() {
        given:
        persons.addAll([jan, piotr, janBis, adam, krzysztof])

        when:
        persons.removeAll([jan, janBis, piotr])

        then:
        persons.size() == 2
        persons.containsAll([adam, krzysztof])
        persons.contains(jan) == false
        persons.contains(piotr) == false
    }

    def "RetainAll"() {
    }

    def "ContainsAll"() {
        when:
        persons.addAll([jan, janBis, piotr])

        then:
        persons.containsAll([jan, janBis, piotr])
        persons.containsAll([jan, piotr])
        persons.containsAll([jan, krzysztof]) == false
    }

    def "ToArray with array"() {
        given:
        Person[] template = new Person[0];
        persons.addAll([jan, janBis, piotr])

        when:
        Person[] result = persons.toArray(template)

        then:
        result.size() == 2
        result.contains(jan)
        result.contains(janBis)
        result.contains(piotr)
        result.contains(adam) == false

    }

    def "bigger data test"() {

        given:
        def referenceAllData = new ArrayList()
        def referenceJanData = new ArrayList()
        def referenceAdamData = new ArrayList()

        when:
        for (int i = 0; i < 3 * persons.tab.length; i++) {
            def jan = new Person("Jan", "Nowak", i)
            def adam = new Person("Adam", "Kowalski", i)
            persons.add(jan)
            persons.add(adam)
            referenceAllData.add(jan)
            referenceAllData.add(adam)
            referenceJanData.add(jan)
            referenceAdamData.add(adam)
        }
        for (int i = 0; i < 3 * persons.tab.length; i++) {
            persons.add(new Person("Jan", "Nowak", i))
            persons.add(new Person("Adam", "Kowalski", i))
        }

        then:
        persons.size() == 3 * persons.tab.length * 2
        persons.containsAll(referenceAllData)
        referenceAllData.containsAll(persons)
        referenceAllData.containsAll(persons.toList())
        referenceAllData.containsAll(persons.toArray())

        when:
        for (int i = 0; i < 3 * persons.tab.length; i++) {
            persons.remove(new Person("Adam", "Kowalski", i))
        }

        then:
        persons.size() == referenceJanData.size()
        persons.containsAll(referenceJanData)
        referenceJanData.containsAll(persons)
        ! persons.containsAll(referenceAllData)

    }
}
