public class test262 {

	@Test
	void equalsAndHashCode() {
		PostgreSQLContainer<?> container1 = mock(PostgreSQLContainer.class);
		PostgreSQLContainer<?> container2 = mock(PostgreSQLContainer.class);
		MergedAnnotation<ServiceConnection> annotation1 = MergedAnnotation.of(ServiceConnection.class,
				Map.of("name", "", "type", new Class<?>[0]));
		MergedAnnotation<ServiceConnection> annotation2 = MergedAnnotation.of(ServiceConnection.class,
				Map.of("name", "", "type", new Class<?>[0]));
		MergedAnnotation<ServiceConnection> annotation3 = MergedAnnotation.of(ServiceConnection.class,
				Map.of("name", "", "type", new Class<?>[] { JdbcConnectionDetails.class }));
		// Connection Names
		ServiceConnectionContextCustomizer n1 = createConnectionContextCustomizer("test", "name", annotation1, container1);
		ServiceConnectionContextCustomizer n2 = createConnectionContextCustomizer("test", "name", annotation1, container1);
		ServiceConnectionContextCustomizer n3 = createConnectionContextCustomizer("test", "namex", annotation1, container1);
		assertThat(n1.hashCode()).isEqualTo(n2.hashCode()).isNotEqualTo(n3.hashCode());
		assertThat(n1).isEqualTo(n2).isNotEqualTo(n3);
		// Connection Details Types
		ServiceConnectionContextCustomizer t1 = createConnectionContextCustomizer("test", "name", annotation1, container1);
		ServiceConnectionContextCustomizer t2 = createConnectionContextCustomizer("test", "name", annotation2, container1);
		ServiceConnectionContextCustomizer t3 = createConnectionContextCustomizer("test", "name", annotation3, container1);
		assertThat(t1.hashCode()).isEqualTo(t2.hashCode()).isNotEqualTo(t3.hashCode());
		assertThat(t1).isEqualTo(t2).isNotEqualTo(t3);
		// Container
		ServiceConnectionContextCustomizer c1 = createConnectionContextCustomizer("test", "name", annotation1, container1);
		ServiceConnectionContextCustomizer c2 = createConnectionContextCustomizer("test", "name", annotation1, container1);
		ServiceConnectionContextCustomizer c3 = createConnectionContextCustomizer("test", "name", annotation1, container2);
		assertThat(c1.hashCode()).isEqualTo(c2.hashCode()).isNotEqualTo(c3.hashCode());
		assertThat(c1).isEqualTo(c2).isNotEqualTo(c3);
	}

	private ServiceConnectionContextCustomizer createConnectionContextCustomizer(String test, String name,
			MergedAnnotation<ServiceConnection> annotation, PostgreSQLContainer<?> container) {
		return new ServiceConnectionContextCustomizer(
				List.of(new ContainerConnectionSource<>(test, this.origin, PostgreSQLContainer.class, name,
						annotation, () -> container, null, null)));
	}
}
