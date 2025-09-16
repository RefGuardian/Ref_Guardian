public class test160 {

    @Bean
    ArtemisConnectionDetails activemqConnectionDetails() {
        return createArtemisConnectionDetails();
    }

    private ArtemisConnectionDetails createArtemisConnectionDetails() {
        return new ArtemisConnectionDetails() {
    
            @Override
            public ArtemisMode getMode() {
                return ArtemisMode.NATIVE;
            }
    
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
