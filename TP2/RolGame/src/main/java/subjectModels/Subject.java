package subjectModels;

public interface Subject extends Comparable<Subject> {
    public Subject getRandom();
    public Subject cloneSubject();

    public double compareProperty(Subject gs, int propertyIndex);
    public boolean isPropertySimilar(Subject gs, int propertyIndex);
    public int getPropertyCount();
    public Object getProperty(int propertyIndex);
    public void setProperty(Object property, int propertyIndex);
    public String toString();
}
