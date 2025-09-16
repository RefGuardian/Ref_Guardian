class One {
    static boolean truth = true;
    static void important() {
        System.out.println(1);
    }
}
class Two extends One {
    static boolean truth = false;
    Two() {
        important();
        System.out.println(truth);
    }
}
class Three {
    static void important() {
        System.out.println(2);
    }
}