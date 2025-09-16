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
            CollectRequest collectRequest = createCollectRequest(remoteRepository, createDependencies(coordinates));
            DependencyRequest dependencyRequest = createDependencyRequest(collectRequest);
            try {
                List<URL> resolvedArtifacts = resolveArtifacts(repositorySystem, session, dependencyRequest);
                return resolvedArtifacts;
            } catch (Exception ex) {
                latestFailure = ex;
            }
        }
        throw new IllegalStateException("Resolution failed after " + MAX_RESOLUTION_ATTEMPTS + " attempts",
                latestFailure);
    }

    private static CollectRequest createCollectRequest(RemoteRepository remoteRepository, List<Dependency> dependencies) {
        return new CollectRequest(null, Arrays.asList(remoteRepository))
            .setDependencies(dependencies);
    }

    private static DependencyRequest createDependencyRequest(CollectRequest collectRequest) {
        return new DependencyRequest(collectRequest, null);
    }

    private static List<URL> resolveArtifacts(RepositorySystem repositorySystem, DefaultRepositorySystemSession session, DependencyRequest dependencyRequest) throws DependencyResolutionException {
        DependencyResult result = repositorySystem.resolveDependencies(session, dependencyRequest);
        List<URL> resolvedArtifacts = new ArrayList<>();
        for (ArtifactResult artifact : result.getArtifactResults()) {
            resolvedArtifacts.add(artifact.getArtifact().getFile().toURI().toURL());
        }
        return resolvedArtifacts;
    }
}
