public class test107 {

    @Bean
    RabbitConnectionDetails rabbitConnectionDetails() {
        return createRabbitConnectionDetails();
    }

    private RabbitConnectionDetails createRabbitConnectionDetails() {
        return new RabbitConnectionDetails() {

            @Override
            public String getUsername() {
                return "user-1";
            }

            @Override
            public String getPassword() {
                return "password-1";
            }

            @Override
            public String getVirtualHost() {
                return "/vhost-1";
            }

            @Override
            public List<Address> getAddresses() {
                return List.of(new Address("rabbit.example.com", 12345), new Address("rabbit2.example.com", 23456));
            }

        };
    }
}
