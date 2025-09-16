public class test242 {

    Configuration createConfiguration() {
    		ConfigurationImpl configuration = new ConfigurationImpl();
    		configuration.setSecurityEnabled(false);
    		configuration.setPersistenceEnabled(this.properties.isPersistent());
    		String dataDir = getDataDir();
    		configuration.setJournalDirectory(dataDir + "/journal");
    		if (this.properties.isPersistent()) {
    			configuration.setJournalType(JournalType.NIO);
    			configuration.setLargeMessagesDirectory(dataDir + "/largemessages");
    			configuration.setBindingsDirectory(dataDir + "/bindings");
    			configuration.setPagingDirectory(dataDir + "/paging");
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
    		configuration.addAddressSetting("#", new AddressSettings().setDeadLetterAddress(SimpleString.of("DLQ"))
    			.setExpiryAddress(SimpleString.of("ExpiryQueue")));
    		return configuration;
    	}
}
