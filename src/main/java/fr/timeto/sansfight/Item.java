package fr.timeto.sansfight;

public class Item {
    String name;
    String abbreviation;
    String description;
    String use;

    public Item(String name, String abbreviation, String desc, String use) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = desc;
        this.use = use;

    }

    public static class Weapon extends Item {
        int atk;

        public Weapon(String name, String abbreviation, String desc, String use, int atk) {
            super(name, abbreviation, desc, use);
            this.atk = atk;

        }
    }

    public static class Armor extends Item {
        int df;

        public Armor(String name, String abbreviation, String desc, String use, int df) {
            super(name, abbreviation, desc, use);
            this.df = df;

        }
    }

    public static class Consumable extends Item {
        int heal;

        public Consumable(String name, String abbreviation, String desc, String use, int healHp) {
            super(name, abbreviation, desc, use);
            this.heal = healHp;
        }
    }
}
