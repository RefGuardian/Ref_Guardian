interface Animal {
    default String identifyMyself() {
        return "Mammal";
    }
}

interface Mammal extends Animal {
}