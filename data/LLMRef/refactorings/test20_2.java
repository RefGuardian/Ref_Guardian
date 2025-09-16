public class Test20 {

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                pushDelegate();
                try {
                    base.evaluate();
                }
                finally {
                    try {
                        if (!matchersEmpty()) {
                            String output = delegateToString();
                            assertThatOutput(output);
                        }
                    }
                    finally {
                        popDelegate();
                    }
                }
            }
        };
    }

    private void pushDelegate() {
        OutputCaptureRule.this.delegate.push();
    }

    private void popDelegate() {
        OutputCaptureRule.this.delegate.pop();
    }

    private boolean matchersEmpty() {
        return OutputCaptureRule.this.matchers.isEmpty();
    }

    private String delegateToString() {
        return OutputCaptureRule.this.delegate.toString();
    }

    private void assertThatOutput(String output) {
        MatcherAssert.assertThat(output, allOf(OutputCaptureRule.this.matchers));
    }
}
