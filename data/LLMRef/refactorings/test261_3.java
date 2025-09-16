public class test261 {

    @Test
    void startWhenParallelStartsDependenciesOnlyOnce() {
        List<TestStartable> dependencies = createTestStartables(10);
        TestStartable first = new TestStartable(dependencies);
        TestStartable second = new TestStartable(dependencies);
        List<TestStartable> startables = List.of(first, second);
        assertThat(first.getStartCount()).isZero();
        assertThat(second.getStartCount()).isZero();
        for (int i = 0; i < startables.size(); i++) {
            assertThat(dependencies.get(i).getStartCount()).isZero();
        }
        TestcontainersStartup.PARALLEL.start(startables);
        assertThat(first.getStartCount()).isOne();
        assertThat(second.getStartCount()).isOne();
        for (int i = 0; i < startables.size(); i++) {
            assertThat(dependencies.get(i).getStartCount()).isOne();
        }
        TestcontainersStartup.PARALLEL.start(startables);
        assertThat(first.getStartCount()).isOne();
        assertThat(second.getStartCount()).isOne();
        for (int i = 0; i < startables.size(); i++) {
            assertThat(dependencies.get(i).getStartCount()).isOne();
        }
    }

    private List<TestStartable> createTestStartables(int num) {
        // extracted method
        List<TestStartable> dependencies = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            dependencies.add(new TestStartable());
        }
        return dependencies;
    }
}
