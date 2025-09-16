public class test20 {

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                delegatePush();
                try {
                    base.evaluate();
                }
                finally {
                    try {
                        if (!matchersIsEmpty()) {
                            String output = delegateToString();
                            MatcherAssert.assertThat(output, allOf(matchers));
                        }
                    }
                    finally {
                        delegatePop();
                    }
                }
            }

            private void delegatePush() {
                OutputCaptureRule.this.delegate.push();
            }

            private boolean matchersIsEmpty() {
                return OutputCaptureRule.this.matchers.isEmpty();
            }

            private String delegateToString() {
                return OutputCaptureRule.this.delegate.toString();
            }

            private void delegatePop() {
                OutputCaptureRule.this.delegate.pop();
            }
        };
    }
}
