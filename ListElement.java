/**
 * Created by j on 05/06/2016.
 */
public class ListElement {
    private ListElement next;
    private ListElement prev;

    protected boolean isEmpty () {
        return this.next.equals(this)
                ? true
                : false;
    }

    public ListElement (){
        this.next = this;
        this.prev = this;
    }

    public ListElement nextElement() {
        return next;
    }

    public ListElement prevElement() {
        return prev;
    }

    public void setNext(ListElement next) {
        this.next = next;
    }

    public void setPrev(ListElement prev) {
        this.prev = prev;
    }

    public ListElement insertBefore (ListElement element) {
        if (element == null) return null;

        element.setNext(this.next);
        this.next.setPrev(element);
        this.next = element;
        element.setPrev(this);

        return element;
    }

    public ListElement remove () {
        if (isEmpty()) return this.remove();
        prev.setNext(next);
        next.setPrev(prev);
        return this;
    }
}
