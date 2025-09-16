public class test13 {

    @Override
    	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
    			throws IOException {
    		if (isTestConfiguration(metadataReader)) {
    			return true;
    		}
    		if (isTestClass(metadataReader)) {
    			return true;
    		}
    		boolean checkEnclosing = checkEnclosing(metadataReader, metadataReaderFactory);
    		if (checkEnclosing) {
    			return true;
    		}
    		return false;
    	}

    private boolean checkEnclosing(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
    			throws IOException {
    		String enclosing = metadataReader.getClassMetadata().getEnclosingClassName();
    		if (enclosing != null) {
    			try {
    				if (match(metadataReaderFactory.getMetadataReader(enclosing), metadataReaderFactory)) {
    					return true;
    				}
    			}
    			catch (Exception ex) {
    				// Ignore
    			}
    		}
    		return false;
    	}
}
