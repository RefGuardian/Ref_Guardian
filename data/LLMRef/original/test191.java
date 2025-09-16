public class test191 {

    private RelyingPartyRegistration asRegistration(String id, Registration properties) {
    		boolean usingMetadata = StringUtils.hasText(properties.getAssertingparty().getMetadataUri());
    		Builder builder = (!usingMetadata) ? RelyingPartyRegistration.withRegistrationId(id)
    				: createBuilderUsingMetadata(properties.getAssertingparty()).registrationId(id);
    		builder.assertionConsumerServiceLocation(properties.getAcs().getLocation());
    		builder.assertionConsumerServiceBinding(properties.getAcs().getBinding());
    		builder.assertingPartyMetadata(mapAssertingParty(properties.getAssertingparty()));
    		builder.signingX509Credentials((credentials) -> properties.getSigning()
    			.getCredentials()
    			.stream()
    			.map(this::asSigningCredential)
    			.forEach(credentials::add));
    		builder.decryptionX509Credentials((credentials) -> properties.getDecryption()
    			.getCredentials()
    			.stream()
    			.map(this::asDecryptionCredential)
    			.forEach(credentials::add));
    		builder.assertingPartyMetadata(
    				(details) -> details.verificationX509Credentials((credentials) -> properties.getAssertingparty()
    					.getVerification()
    					.getCredentials()
    					.stream()
    					.map(this::asVerificationCredential)
    					.forEach(credentials::add)));
    		builder.singleLogoutServiceLocation(properties.getSinglelogout().getUrl());
    		builder.singleLogoutServiceResponseLocation(properties.getSinglelogout().getResponseUrl());
    		builder.singleLogoutServiceBinding(properties.getSinglelogout().getBinding());
    		builder.entityId(properties.getEntityId());
    		builder.nameIdFormat(properties.getNameIdFormat());
    		RelyingPartyRegistration registration = builder.build();
    		boolean signRequest = registration.getAssertingPartyMetadata().getWantAuthnRequestsSigned();
    		validateSigningCredentials(properties, signRequest);
    		return registration;
    	}
}
