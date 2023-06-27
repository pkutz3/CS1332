public class KutzTestHW2 {
    public static void main(String[] args) {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        Object[] array = new Object[100];

        for (int i = 0; i < 100; i++) {
            list.addToBack(Integer.toString(i));
        } // for

        System.out.println(list.removeAtIndex(0));
    }
}
