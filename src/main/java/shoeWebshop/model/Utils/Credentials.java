package shoeWebshop.model.Utils;

public enum Credentials {

    SENDER_EMAIL("nackademinJava20A@gmail.com"),
    SENDER_PASSWORD("YS8]a8u>Hc|ZC3kNo");

    private final String s;

    Credentials(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
