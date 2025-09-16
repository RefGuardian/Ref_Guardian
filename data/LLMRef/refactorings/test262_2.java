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
    		ServiceConnectionContextCustomizer n1 = createServiceConnectionContextCustomizer(container1, annotation1);
    		ServiceConnectionContextCustomizer n2 = createServiceConnectionContextCustomizer(container1, annotation1);
    		ServiceConnectionContextCustomizer n3 = createServiceConnectionContextCustomizer(container1, annotation1);
    		assertThat(n1.hashCode()).isEqualTo(n2.hashCode()).isNotEqualTo(n3.hashCode());
    		assertThat(n1).isEqualTo(n2).isNotEqualTo(n3);
    		// Connection Details Types
    		ServiceConnectionContextCustomizer t1 = createServiceConnectionContextCustomizer(container1, annotation1);
    		ServiceConnectionContextCustomizer t2 = createServiceConnectionContextCustomizer(container1, annotation2);
    		ServiceConnectionContextCustomizer t3 = createServiceConnectionContextCustomizer(container1, annotation3);
    		assertThat(t1.hashCode()).isEqualTo(t2.hashCode()).isNotEqualTo(t3.hashCode());
    		assertThat(t1).isEqualTo(t2).isNotEqualTo(t3);
    		// Container
    		ServiceConnectionContextCustomizer c1 = createServiceConnectionContextCustomizer(container1, annotation1);
    		ServiceConnectionContextCustomizer c2 = createServiceConnectionContextCustomizer(container1, annotation1);
    		ServiceConnectionContextCustomizer c3 = createServiceConnectionContextCustomizer(container2, annotation1);
    		assertThat(c1.hashCode()).isEqualTo(c2.hashCode()).isNotEqualTo(c3.hashCode());
    		assertThat(c1).isEqualTo(c2).isNotEqualTo(c3);
    	}

    private ServiceConnectionContextCustomizer createServiceConnectionContextCustomizer(PostgreSQLContainer<?> container, MergedAnnotation<ServiceConnection> annotation) {
        return new ServiceConnectionContextCustomizer(
            List.of(new ContainerConnectionSource<>("test", this.origin, PostgreSQLContainer.class, "name",
                    annotation, () -> container, null, null)));
    }
}
