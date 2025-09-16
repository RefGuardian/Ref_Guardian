public class test20 {

    @Override
    	public Statement apply(Statement base, Description description) {
    		return new Statement() {
    			@Override
    			public void evaluate() throws Throwable {
    				OutputCaptureRule.this.delegate.push();
    				try {
    					base.evaluate();
    				}
    				finally {
    					try {
    						if (!OutputCaptureRule.this.matchers.isEmpty()) {
    							String output = OutputCaptureRule.this.delegate.toString();
    							MatcherAssert.assertThat(output, allOf(OutputCaptureRule.this.matchers));
    						}
    					}
    					finally {
    						OutputCaptureRule.this.delegate.pop();
    					}
    				}
    			}
    		};
    	}
}
