public class test102 {

    @Test
	@SuppressWarnings("unchecked")
	void testConnectionFactoryWithOverrides() {
		this.contextRunner.withUserConfiguration(TestConfiguration.class)
			.withPropertyValues("spring.rabbitmq.host:remote-server", "spring.rabbitmq.port:9000",
					"spring.rabbitmq.address-shuffle-mode=random", "spring.rabbitmq.username:alice",
					"spring.rabbitmq.password:secret", "spring.rabbitmq.virtual_host:/vhost",
					"spring.rabbitmq.connection-timeout:123", "spring.rabbitmq.channel-rpc-timeout:140",
					"spring.rabbitmq.max-inbound-message-body-size:128MB")
			.run((context) -> {
				CachingConnectionFactory connectionFactory = context.getBean(CachingConnectionFactory.class);
				assertThat(connectionFactory.getHost()).isEqualTo("remote-server");
				assertThat(connectionFactory.getPort()).isEqualTo(9000);
				assertThat(connectionFactory).hasFieldOrPropertyWithValue("addressShuffleMode",
						AddressShuffleMode.RANDOM);
				assertThat(connectionFactory.getVirtualHost()).isEqualTo("/vhost");
				com.rabbitmq.client.ConnectionFactory rcf = connectionFactory.getRabbitConnectionFactory();
				assertThat(rcf.getConnectionTimeout()).isEqualTo(123);
				assertThat(rcf.getChannelRpcTimeout()).isEqualTo(140);
				assertThat((List<Address>) ReflectionTestUtils.getField(connectionFactory, "addresses")).hasSize(1);
				assertThat(rcf).hasFieldOrPropertyWithValue("maxInboundMessageBodySize", 1024 * 1024 * 128);
			});
	}

	private void assertConnectionDetails(CachingConnectionFactory connectionFactory, String host, int port,
			AddressShuffleMode shuffleMode, String username, String password, String virtualHost, int connectionTimeout,
			int channelRpcTimeout, int maxInboundMessageBodySize) {
		assertThat(connectionFactory.getHost()).isEqualTo(host);
		assertThat(connectionFactory.getPort()).isEqualTo(port);
		assertThat(connectionFactory).hasFieldOrPropertyWithValue("addressShuffleMode", shuffleMode);
		assertThat(connectionFactory.getVirtualHost()).isEqualTo(virtualHost);
		com.rabbitmq.client.ConnectionFactory rcf = connectionFactory.getRabbitConnectionFactory();
		assertThat(rcf.getConnectionTimeout()).isEqualTo(connectionTimeout);
		assertThat(rcf.getChannelRpcTimeout()).isEqualTo(channelRpcTimeout);
		assertThat((List<Address>) ReflectionTestUtils.getField(connectionFactory, "addresses")).hasSize(1);
		assertThat(rcf).hasFieldOrPropertyWithValue("maxInboundMessageBodySize", 1024 * 1024 * maxInboundMessageBodySize);
	}
}
