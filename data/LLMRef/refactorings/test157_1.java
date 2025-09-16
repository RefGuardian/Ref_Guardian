public class test157 {

    @Bean
    ActiveMQConnectionDetails activemqConnectionDetails() {
        return createActiveMQConnectionDetails();
    }

    private ActiveMQConnectionDetails createActiveMQConnectionDetails() {
        return new ActiveMQConnectionDetails() {

            @Override
            public String getBrokerUrl() {
                return "tcp://localhost:12345";
            }

            @Override
            public String getUser() {
                return "springuser";
            }

            @Override
            public String getPassword() {
                return "spring";
            }

        };
    }
}
