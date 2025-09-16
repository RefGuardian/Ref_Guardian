public class test261 {

    @Test
	void startWhenParallelStartsDependenciesOnlyOnce() {
		List<TestStartable> dependencies = createTestStartables(10);
		TestStartable[] startables = createStartables(dependencies);
		verifyStartCountsAreZero(startables, dependencies);

		TestcontainersStartup.PARALLEL.start(startables);

		verifyStartCountsAreOne(startables, dependencies);

    	TestcontainersStartup.PARALLEL.start(startables);
		verifyStartCountsAreOne(startables, dependencies);
	}

	private TestStartable[] createStartables(List<TestStartable> dependencies) {
	    TestStartable first = new TestStartable(dependencies);
	    TestStartable second = new TestStartable(dependencies);
	    return new TestStartable[]{first, second};
	}

	private void verifyStartCountsAreZero(TestStartable[] startables, List<TestStartable> dependencies) {
	    assertThat(startables[0].getStartCount()).isZero();
	    assertThat(startables[1].getStartCount()).isZero();
	    for (int i = 0; i < startables.length; i++) {
	        assertThat(dependencies.get(i).getStartCount()).isZero();
	    }
	}

	private void verifyStartCountsAreOne(TestStartable[] startables, List<TestStartable> dependencies) {
	    assertThat(startables[0].getStartCount()).isOne();
	    assertThat(startables[1].getStartCount()).isOne();
	    for (int i = 0; i < startables.length; i++) {
	        assertThat(dependencies.get(i).getStartCount()).isOne();
	    }
	}
}
