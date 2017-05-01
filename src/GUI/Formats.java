package GUI;

/**
 * Created by Kyle on 4/21/2017.
 */
public enum Formats {
    JPEG (".jpeg", 0),
    PNG (".png", 1),
    BMP (".bmp", 2),
    SVG (".svg", 3);

    private final String format; //filetype name

    public String getFormat() {
        return format;
    }

    public int getNumber() {
        return number;
    }

    private final int number;

    Formats(String name, int index) {this.format = name; this.number= index;}

    //TODO: make a tree of format types? Image then video?
}
