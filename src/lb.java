class lb {
    float wpm, cps, accuracy, time;
    public lb(float w, float c, float a, float t) {
        wpm = w;
        cps = c;
        accuracy = a;
        time = t;
    }
    public void display() {
        System.out.println(Float.toString(wpm) + Float.toString(cps)
            + Float.toString(accuracy) + Float.toString(time));
    }
}