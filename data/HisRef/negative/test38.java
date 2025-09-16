interface Animal {
    String identifyMyself();
}

interface Mammal extends Animal {
    default String identifyMyself() {
        return "Mammal";
    }
}