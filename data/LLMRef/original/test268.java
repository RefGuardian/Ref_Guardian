public class test268 {

    @Test
    	void whenAddPackageAndDeleteThenResourcesDoNotExistAndCannotBeFound() {
    		this.resources.addPackage(getClass().getPackage(),
    				new String[] { "resource-1.txt", "resource-2.txt", "sub/resource-3.txt" });
    		assertThat(this.root.resolve("resource-1.txt")).hasContent("one");
    		assertThat(this.root.resolve("resource-2.txt")).hasContent("two");
    		assertThat(this.root.resolve("sub/resource-3.txt")).hasContent("three");
    		assertThat(this.resources.find("resource-1.txt")).isNotNull();
    		assertThat(this.resources.find("resource-2.txt")).isNotNull();
    		assertThat(this.resources.find("sub/resource-3.txt")).isNotNull();
    		assertThat(this.resources.find("sub/")).isNotNull();
    		this.resources.delete();
    		assertThat(this.root.resolve("resource-1.txt")).doesNotExist();
    		assertThat(this.root.resolve("resource-2.txt")).doesNotExist();
    		assertThat(this.root.resolve("sub/resource-3.txt")).doesNotExist();
    		assertThat(this.root.resolve("sub")).doesNotExist();
    		assertThat(this.resources.find("resource-1.txt")).isNull();
    		assertThat(this.resources.find("resource-2.txt")).isNull();
    		assertThat(this.resources.find("sub/resource-3.txt")).isNull();
    		assertThat(this.resources.find("sub/")).isNull();
    	}
}
