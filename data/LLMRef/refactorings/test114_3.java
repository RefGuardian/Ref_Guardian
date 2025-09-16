public class test114 {

    @Test
    void recordConditionEvaluations() {
        ConditionOutcome outcome1 = new ConditionOutcome(false, "m1");
        ConditionOutcome outcome2 = new ConditionOutcome(false, "m2");
        ConditionOutcome outcome3 = new ConditionOutcome(false, "m3");
        this.report.recordConditionEvaluation("a", this.condition1, outcome1);
        this.report.recordConditionEvaluation("a", this.condition2, outcome2);
        this.report.recordConditionEvaluation("b", this.condition3, outcome3);
        Map<String, ConditionAndOutcomes> map = this.report.getConditionAndOutcomesBySource();
        assertThat(map).hasSize(2);
        Iterator<ConditionAndOutcome> iterator = map.get("a").iterator();
        ConditionAndOutcome conditionAndOutcome = iterator.next();
        assertThat(conditionAndOutcome.getCondition()).isEqualTo(this.condition1);
        assertThat(conditionAndOutcome.getOutcome()).isEqualTo(outcome1);
        conditionAndOutcome = iterator.next();
        assertThat(conditionAndOutcome.getCondition()).isEqualTo(this.condition2);
        assertThat(conditionAndOutcome.getOutcome()).isEqualTo(outcome2);
        assertThat(iterator.hasNext()).isFalse();
        iterator = map.get("b").iterator();
        conditionAndOutcome = iterator.next();
        assertThat(conditionAndOutcome.getCondition()).isEqualTo(this.condition3);
        assertThat(conditionAndOutcome.getOutcome()).isEqualTo(outcome3);
        assertThat(iterator.hasNext()).isFalse();
    }
}
