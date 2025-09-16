public class test242 {

    Configuration createConfiguration() {
        ConfigurationImpl configuration = new ConfigurationImpl();
        configuration.setSecurityEnabled(false);
        boolean isPersistent = this.properties.isPersistent();
        configuration.setPersistenceEnabled(isPersistent);
        final String dataDir = getDataDir();
        final String journalDir = dataDir + "/journal";
        configuration.setJournalDirectory(journalDir);
        if (isPersistent) {
            configuration.setJournalType(JournalType.NIO);
            String largeMessagesDir = dataDir + "/largemessages";
            configuration.setLargeMessagesDirectory(largeMessagesDir);
            String bindingsDir = dataDir + "/bindings";
            configuration.setBindingsDirectory(bindingsDir);
            String pagingDir = dataDir + "/paging";
            configuration.setPagingDirectory(pagingDir);
        }
        TransportConfiguration transportConfiguration = new TransportConfiguration(InVMAcceptorFactory.class.getName(),
                this.properties.generateTransportParameters());
        configuration.getAcceptorConfigurations().add(transportConfiguration);
        if (this.properties.isDefaultClusterPassword() && logger.isDebugEnabled()) {
            logger.debug("Using default Artemis cluster password: " + this.properties.getClusterPassword());
        }
        configuration.setClusterPassword(this.properties.getClusterPassword());
        configuration.addAddressConfiguration(createAddressConfiguration("DLQ"));
        configuration.addAddressConfiguration(createAddressConfiguration("ExpiryQueue"));
        AddressSettings addressSettings = new AddressSettings().setDeadLetterAddress(SimpleString.of("DLQ"))
                .setExpiryAddress(SimpleString.of("ExpiryQueue"));
        configuration.addAddressSetting("#", addressSettings);
        return configuration;
    }
}
