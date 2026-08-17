public class Task {
    protected String name;
    protected boolean flag;

    public Task(String name) {
        this.name = name;
        this.flag = false;
    }

    public void mark() {
        this.flag = true;
    }

    public void unmark() {
        this.flag = false;
    }

    @Override
    public String toString() {
        String mark = flag ? "\uD83D\uDC1F" : "\uD83C\uDFA3";
        return String.format("[%s] %s", mark, name);
    }
}
