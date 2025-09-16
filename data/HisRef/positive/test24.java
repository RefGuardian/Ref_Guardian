public class test24 {
    public boolean isConsideredFinal(int cl) {
        return cl == cl1()
                && countSeenDirectlyAtCl() == countExpectedDirectlyAtCl();
    }
}