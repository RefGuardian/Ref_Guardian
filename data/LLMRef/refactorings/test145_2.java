public class test145 {

    @Test
    	@SuppressWarnings("unchecked")
    	void customizeProducerBuilder() {
    		PulsarProperties properties = new PulsarProperties();
    		ProducerBuilder<Object> builder = mock(ProducerBuilder.class);
    		new PulsarPropertiesMapper(properties).customizeProducerBuilder(builder);
    		then(builder).should().producerName("name");
    		then(builder).should().topic("topicname");
    		then(builder).should().sendTimeout(1000, TimeUnit.MILLISECONDS);
    		then(builder).should().messageRoutingMode(MessageRoutingMode.RoundRobinPartition);
    		then(builder).should().hashingScheme(HashingScheme.JavaStringHash);
    		then(builder).should().enableBatching(false);
    		then(builder).should().enableChunking(true);
    		then(builder).should().compressionType(CompressionType.SNAPPY);
    		then(builder).should().accessMode(ProducerAccessMode.Exclusive);
    	}
}
