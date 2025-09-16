public class test140 {

    @Test
    	void customizeShouldCheckGeneric() {
    		List<TestCustomizer<?>> list = new ArrayList<>();
    		list.add(new TestCustomizer<>());
    		list.add(new TestPulsarListenersContainerFactoryCustomizer());
    		list.add(new TestConcurrentPulsarListenerContainerFactoryCustomizer());
    		PulsarContainerFactoryCustomizers customizers = new PulsarContainerFactoryCustomizers(list);
    
    		customizers.customize(mock(PulsarContainerFactory.class));
    		assertThat(list.get(0).getCount()).isOne();
    		assertThat(list.get(1).getCount()).isZero();
    		assertThat(list.get(2).getCount()).isZero();
    
    		customizers.customize(mock(ConcurrentPulsarListenerContainerFactory.class));
    		assertThat(list.get(0).getCount()).isEqualTo(2);
    		assertThat(list.get(1).getCount()).isOne();
    		assertThat(list.get(2).getCount()).isOne();
    
    		customizers.customize(mock(DefaultReactivePulsarListenerContainerFactory.class));
    		assertThat(list.get(0).getCount()).isEqualTo(3);
    		assertThat(list.get(1).getCount()).isEqualTo(2);
    		assertThat(list.get(2).getCount()).isOne();
    
    		customizers.customize(mock(DefaultPulsarReaderContainerFactory.class));
    		assertThat(list.get(0).getCount()).isEqualTo(4);
    		assertThat(list.get(1).getCount()).isEqualTo(2);
    		assertThat(list.get(2).getCount()).isOne();
    	}
}
