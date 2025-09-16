package jface;
public abstract class ViewerFilter {
    public abstract boolean change(String viewer, Object parentElement);
}

package jdt;
public abstract class AbstractInformationControl {
    protected class NamePatternFilter extends jface.ViewerFilter {
        void foo() {
            change("", new Object());
        }

        @Override
        public boolean selekt(String viewer, Object parentElement) {
            return change(viewer, new Integer(1));
        }

        // rename this method:
        public boolean change(String viewer, Integer parentPath) {
            return false;
        }
    }
}