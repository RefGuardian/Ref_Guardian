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
		ContainerConnectionSource<PostgreSQLContainer<?>> containerConnectionSource = new ContainerConnectionSource<>("test", this.origin, PostgreSQLContainer.class, "name",
				annotation1, () -> container1, null, null);
		ServiceConnectionContextCustomizer n1 = new ServiceConnectionContextCustomizer(
				List.of(containerConnectionSource));
		ServiceConnectionContextCustomizer n2 = new ServiceConnectionContextCustomizer(
				List.of(containerConnectionSource));
		ServiceConnectionContextCustomizer n3 = new ServiceConnectionContextCustomizer(
				List.of(new ContainerConnectionSource<>("test", this.origin, PostgreSQLContainer.class, "namex",
						annotation1, () -> container1, null, null));
		assertThat(n1.hashCode()).isEqualTo(n2.hashCode()).isNotEqualTo(n3.hashCode());
		assertThat(n1).isEqualTo(n2).isNotEqualTo(n3);
		// Connection Details Types
		ServiceConnectionContextCustomizer t1 = new ServiceConnectionContextCustomizer(
				List.of(containerConnectionSource));
		ServiceConnectionContextCustomizer t2 = new ServiceConnectionContextCustomizer(
				List.of(new ContainerConnectionSource<>("test", this.origin, PostgreSQLContainer.class, "name",
						annotation2, () -> container1, null, null));
		ServiceConnectionContextCustomizer t3 = new ServiceConnectionContextCustomizer(
				List.of(new ContainerConnectionSource<>("test", this.origin, PostgreSQLContainer.class, "name",
						annotation3, () -> container1, null, null));
		assertThat(t1.hashCode()).isEqualTo(t2.hashCode()).isNotEqualTo(t3.hashCode());
		assertThat(t1).isEqualTo(t2).isNotEqualTo(t3);
		// Container
		ServiceConnectionContextCustomizer c1 = new ServiceConnectionContextCustomizer(
				List.of(containerConnectionSource));
		ServiceConnectionContextCustomizer c2 = new ServiceConnectionContextCustomizer(
				List.of(containerConnectionSource));
		ServiceConnectionContextCustomizer c3 = new ServiceConnectionContextCustomizer(
				List.of(new ContainerConnectionSource<>("test", this.origin, PostgreSQLContainer.class, "name",
						annotation1, () -> container2, null, null));
		assertThat(c1.hashCode()).isEqualTo(c2.hashCode()).isNotEqualTo(c3.hashCode());
		assertThat(c1).isEqualTo(c2).isNotEqualTo(c3);
	}
}
