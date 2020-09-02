package subjectModels;

public interface Subject extends Comparable<Subject> {
    Subject getRandom();
    Subject cloneSubject();

    double compareProperty(Subject gs, int propertyIndex);
    boolean isPropertySimilar(Subject gs, int propertyIndex);
    int getPropertyCount();
    Object getProperty(int propertyIndex);
    void setProperty(Object property, int propertyIndex);
    String toString();
    double getFitness();
    void mutateProperty(int index);
}
