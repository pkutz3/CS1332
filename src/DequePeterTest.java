public class DequePeterTest {
    public static void main(String[] args) {
        ArrayDeque<String> array = new ArrayDeque<>();

        array.addLast("1a");
        array.addLast("2a");
        array.addLast("3a");
        array.addLast("4a");


        System.out.println(array.getFirst());
        System.out.println(array.getLast());
    }
}
