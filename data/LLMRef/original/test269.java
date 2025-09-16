public class test269 {

    private static List<URL> resolveCoordinates(String[] coordinates) {
    		Exception latestFailure = null;
    		RepositorySystem repositorySystem = createRepositorySystem();
    		DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();
    		session.setSystemProperties(System.getProperties());
    		LocalRepository localRepository = new LocalRepository(System.getProperty("user.home") + "/.m2/repository");
    		RemoteRepository remoteRepository = new RemoteRepository.Builder("central", "default",
    				"https://repo.maven.apache.org/maven2")
    			.build();
    		session.setLocalRepositoryManager(repositorySystem.newLocalRepositoryManager(session, localRepository));
    		for (int i = 0; i < MAX_RESOLUTION_ATTEMPTS; i++) {
    			CollectRequest collectRequest = new CollectRequest(null, Arrays.asList(remoteRepository));
    			collectRequest.setDependencies(createDependencies(coordinates));
    			DependencyRequest dependencyRequest = new DependencyRequest(collectRequest, null);
    			try {
    				DependencyResult result = repositorySystem.resolveDependencies(session, dependencyRequest);
    				List<URL> resolvedArtifacts = new ArrayList<>();
    				for (ArtifactResult artifact : result.getArtifactResults()) {
    					resolvedArtifacts.add(artifact.getArtifact().getFile().toURI().toURL());
    				}
    				return resolvedArtifacts;
    			}
    			catch (Exception ex) {
    				latestFailure = ex;
    			}
    		}
    		throw new IllegalStateException("Resolution failed after " + MAX_RESOLUTION_ATTEMPTS + " attempts",
    				latestFailure);
    	}
}
