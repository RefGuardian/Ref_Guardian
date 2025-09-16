public class test269 {

	private static final LocalRepository localRepository = createLocalRepository();

	private static List<URL> resolveCoordinates(String[] coordinates) {
		Exception latestFailure = null;
		RepositorySystem repositorySystem = createRepositorySystem();
		DefaultRepositorySystemSession session = createDefaultSession();
		session.setSystemProperties(System.getProperties());
		RemoteRepository remoteRepository = createRemoteRepository();
		session.setLocalRepositoryManager(repositorySystem.newLocalRepositoryManager(session, localRepository));
		for (int i = 0; i < MAX_RESOLUTION_ATTEMPTS; i++) {
			CollectRequest collectRequest = createCollectRequest(remoteRepository, coordinates);
			try {
				DependencyResult result = resolveDependencies(repositorySystem, session, collectRequest);
				List<URL> resolvedArtifacts = extractResolvedArtifacts(result);
				return resolvedArtifacts;
			} catch (Exception ex) {
				latestFailure = ex;
			}
		}
		throw new IllegalStateException(
				"Resolution failed after " + MAX_RESOLUTION_ATTEMPTS + " attempts",
				latestFailure);
	}

	private static LocalRepository createLocalRepository() {
		return new LocalRepository(System.getProperty("user.home") + "/.m2/repository");
	}

	private static DefaultRepositorySystemSession createDefaultSession() {
		return MavenRepositorySystemUtils.newSession();
	}

	private static RemoteRepository createRemoteRepository() {
		return new RemoteRepository.Builder("central", "default", "https://repo.maven.apache.org/maven2").build();
	}

	private static CollectRequest createCollectRequest(RemoteRepository remoteRepository, String[] coordinates) {
		CollectRequest collectRequest = new CollectRequest(null, Arrays.asList(remoteRepository));
		collectRequest.setDependencies(createDependencies(coordinates));
		return collectRequest;
	}

	private static DependencyResult resolveDependencies(RepositorySystem repositorySystem,
			DefaultRepositorySystemSession session, CollectRequest collectRequest) {
		DependencyRequest dependencyRequest = new DependencyRequest(collectRequest, null);
		return repositorySystem.resolveDependencies(session, dependencyRequest);
	}

	private static List<URL> extractResolvedArtifacts(DependencyResult result) throws MalformedURLException {
		List<URL> resolvedArtifacts = new ArrayList<>();
		for (ArtifactResult artifact : result.getArtifactResults()) {
			resolvedArtifacts.add(artifact.getArtifact().getFile().toURI().toURL());
		}
		return resolvedArtifacts;
	}
}
