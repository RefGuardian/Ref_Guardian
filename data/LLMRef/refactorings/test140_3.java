public class test140 {

    @Test
	void customizeShouldCheckGeneric() {
		List<TestCustomizer<?>> list = new ArrayList<>();
		list.add(new TestCustomizer<>());
		list.add(new TestPulsarListenersContainerFactoryCustomizer());
		list.add(new TestConcurrentPulsarListenerContainerFactoryCustomizer());
		PulsarContainerFactoryCustomizers customizers = new PulsarContainerFactoryCustomizers(list);
		
		CustomizerTestHelper customizerTestHelper = new CustomizerTestHelper();
		customizerTestHelper.assertThatCountIsCorrect(list, 0, 1, 0);
		customizers.customize(mock(PulsarContainerFactory.class));
		customizerTestHelper.assertThatCountIsCorrect(list, 2, 1, 0);
		customizers.customize(mock(ConcurrentPulsarListenerContainerFactory.class));
		customizerTestHelper.assertThatCountIsCorrect(list, 3, 2, 1);
		customizers.customize(mock(DefaultReactivePulsarListenerContainerFactory.class));
		customizerTestHelper.assertThatCountIsCorrect(list, 4, 2, 1);
		customizers.customize(mock(DefaultPulsarReaderContainerFactory.class));
		customizerTestHelper.assertThatCountIsCorrect(list, 5, 2, 1);
	}
	
	private static class CustomizerTestHelper {
	    
	    void assertThatCountIsCorrect(List<TestCustomizer<?>> list, int count1, int count2, int count3) {
	        assertThat(list.get(0).getCount()).isEqualTo(count1);
	        assertThat(list.get(1).getCount()).isEqualTo(count2);
	        assertThat(list.get(2).getCount()).isEqualTo(count3);
	    }
	}
}
