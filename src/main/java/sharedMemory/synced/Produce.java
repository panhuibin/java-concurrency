package sharedMemory.synced;

public class Produce{

    public static class ProduceBuilder{
        private int instance;
        private Color color;

        public void withInstance(int instance){
            this.instance = instance;
        }

        public void withColor(Color color){
            this.color = color;
        }

        public Produce build(){
            return new Produce(instance, color);
        }
    }
    enum Color {Red, Blue, Green, Yellow};

    private final int instance;
    private final Color color;

    public Produce(int instance, Color color) {
        this.instance = instance;
        this.color = color;
    }

    public int getInstance() {
        return instance;
    }

    public Color getColor() {
        return color;
    }
}
